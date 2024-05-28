package woowacourse.movie.ui.seat

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.movie.R
import woowacourse.movie.data.ReservationTicketDatabase
import woowacourse.movie.data.repository.OfflineReservationRepository
import woowacourse.movie.data.source.DummyScreenDataSource
import woowacourse.movie.data.source.DummySeatsDataSource
import woowacourse.movie.data.source.DummyTheatersDataSource
import woowacourse.movie.databinding.ActivitySeatReservationBinding
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.TimeReservation
import woowacourse.movie.ui.pushnotification.PushNotificationBroadCastReceiver
import woowacourse.movie.ui.reservation.ReservationCompleteActivity
import woowacourse.movie.ui.reservation.ReservationCompleteActivity.Companion.PUT_EXTRA_KEY_RESERVATION_TICKET_ID
import woowacourse.movie.ui.seat.adapter.OnSeatSelectedListener
import woowacourse.movie.ui.seat.adapter.SeatsAdapter
import java.util.concurrent.TimeUnit

class SeatReservationActivity : AppCompatActivity(), SeatReservationContract.View {
    private val binding: ActivitySeatReservationBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_seat_reservation,
        )
    }

    private lateinit var presenter: SeatReservationContract.Presenter
    private lateinit var onReserveButtonClickedListener: OnReserveClickedListener

    private lateinit var seatsAdapter: SeatsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()

        with(presenter) {
            loadAllSeats()
            loadTimeReservation()
        }
        onReserveButtonClickedListener = OnReserveClickedListener { presenter.attemptReserve() }
        binding.onReserveClickedListener = onReserveButtonClickedListener
    }

    private fun initPresenter() {
        val theaterId = intent.getIntExtra(PUT_EXTRA_THEATER_ID_KEY, DEFAULT_THEATER_ID)
        val timeReservationId = intent.getIntExtra(TIME_RESERVATION_ID, DEFAULT_TIME_RESERVATION_ID)
        presenter =
            SeatReservationPresenter(
                this,
                DummyScreenDataSource(DummySeatsDataSource()),
                OfflineReservationRepository(
                    ReservationTicketDatabase.getDatabase(applicationContext).reservationDao(),
                ),
                DummyTheatersDataSource(),
                theaterId,
                timeReservationId,
            )
    }

    override fun showTimeReservation(timeReservation: TimeReservation) {
        binding.timeReservation = timeReservation
    }

    override fun showTotalPrice(totalPrice: Int) {
        binding.totalPrice = totalPrice
    }

    override fun showAllSeats(seats: Seats) {
        val seatsGridLayout = binding.rvSeatReservationSeats
        seatsGridLayout.layoutManager = GridLayoutManager(this, seats.maxColumn())

        seatsAdapter =
            SeatsAdapter(
                object : OnSeatSelectedListener {
                    override fun onSeatSelected(
                        seat: Seat,
                        seatView: View,
                    ) {
                        presenter.selectSeat(seat.position, seatView)
                    }

                    override fun onSeatDeselected(
                        seat: Seat,
                        seatView: View,
                    ) {
                        presenter.deselectSeat(seat.position, seatView)
                    }
                },
            )

        seatsGridLayout.adapter = seatsAdapter

        seatsAdapter.submitList(seats.seats)
    }

    override fun showSelectedSeat(seatView: View) {
        seatView.isSelected = true
        presenter.calculateTotalPrice()
    }

    override fun showDeselectedSeat(seatView: View) {
        seatView.isSelected = false
        presenter.calculateTotalPrice()
    }

    override fun activateReservation(activated: Boolean) {
        with(binding.btnSeatReservationComplete) {
            if (activated) {
                isEnabled = true
                setBackgroundColor(getColor(R.color.complete_activated))
            } else {
                isEnabled = false
                setBackgroundColor(getColor(R.color.complete_deactivated))
            }
        }
    }

    override fun checkReservationConfirm() {
        val alertDialog =
            AlertDialog.Builder(this)
                .setTitle(R.string.check_reservation_title)
                .setMessage(R.string.check_reservation_content)
                .setPositiveButton(R.string.check_reservation_complete) { _, _ ->
                    presenter.reserve()
                }
                .setNegativeButton(R.string.check_reservation_cancel) { dialog, _ ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .create()

        alertDialog.show()
    }

    override fun showCompleteReservation(reservationTicketId: Int) {
        ReservationCompleteActivity.startActivity(this, reservationTicketId)
        Log.d(TAG, "showCompleteReservation: start ReservationCompleteActivity")
    }

    override fun showSeatReservationFail(throwable: Throwable) {
        showToast(throwable)
    }

    override fun showSelectedSeatFail(throwable: Throwable) {
        showToast(throwable)
    }

    override fun setAlarm(
        movieTimeMillis: Long,
        reservationTicketId: Int,
    ) {
        scheduleAlarm(movieTimeMillis, reservationTicketId)
    }

    private fun scheduleAlarm(
        movieTime: Long,
        reservationTicketId: Int,
    ) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent =
            Intent(applicationContext, PushNotificationBroadCastReceiver::class.java).apply {
                putExtra(PUT_EXTRA_KEY_RESERVATION_TICKET_ID, reservationTicketId)
            }
        val pendingIntent = pendingIntent(intent)
        setAlarmForVersion(alarmManager, movieTime, pendingIntent)
    }

    private fun setAlarmForVersion(
        alarmManager: AlarmManager,
        movieTime: Long,
        pendingIntent: PendingIntent,
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    movieTime - TimeUnit.MINUTES.toMillis(30),
                    pendingIntent,
                )
            }
        } else {
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                movieTime - TimeUnit.MINUTES.toMillis(30),
                pendingIntent,
            )
        }
    }

    private fun pendingIntent(intent: Intent): PendingIntent =
        PendingIntent.getBroadcast(
            this,
            PushNotificationBroadCastReceiver.MOVIE_RESERVATION_REMINDER_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )

    private fun showToast(e: Throwable) {
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TIME_RESERVATION_ID = "timeReservationId"
        private const val PUT_EXTRA_THEATER_ID_KEY = "theaterId"

        private const val DEFAULT_TIME_RESERVATION_ID = -1
        private const val DEFAULT_THEATER_ID = -1

        fun startActivity(
            context: Context,
            timeReservationId: Int,
            theaterId: Int,
        ) {
            val intent =
                Intent(context, SeatReservationActivity::class.java).apply {
                    putExtra(TIME_RESERVATION_ID, timeReservationId)
                    putExtra(PUT_EXTRA_THEATER_ID_KEY, theaterId)
                }
            context.startActivity(intent)
        }

        private const val TAG = "SeatReservationActivity"
    }
}

fun interface OnReserveClickedListener {
    fun onClick()
}
