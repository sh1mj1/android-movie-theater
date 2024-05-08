package woowacourse.movie.ui.seat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivitySeatReservationBinding
import woowacourse.movie.domain.model.Position
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.TimeReservation
import woowacourse.movie.domain.repository.DummyReservation
import woowacourse.movie.domain.repository.DummyScreens
import woowacourse.movie.domain.repository.DummySeats
import woowacourse.movie.ui.reservation.ReservationCompleteActivity

class SeatReservationActivity : AppCompatActivity(), SeatReservationContract.View {
    private val binding: ActivitySeatReservationBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_seat_reservation,
        )
    }

    private lateinit var presenter: SeatReservationContract.Presenter
    private lateinit var onReserveButtonClickedListener: OnReserveClickedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_reservation)

        initPresenter()

        with(presenter) {
            loadAllSeats()
            loadTimeReservation()
            onReserveButtonClickedListener = OnReserveClickedListener { reserve() }
            binding.onReserveClickedListener = onReserveButtonClickedListener
        }
    }

    private fun initPresenter() {
        val theaterId = intent.getIntExtra(PUT_EXTRA_THEATER_ID_KEY, DEFAULT_THEATER_ID)
        val timeReservationId = intent.getIntExtra(TIME_RESERVATION_ID, DEFAULT_TIME_RESERVATION_ID)
        presenter =
            SeatReservationPresenter(
                this,
                DummyScreens(DummySeats()),
                DummyReservation,
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
        val seatsGridLayout = binding.glSeatReservationSeats
        val maxRow = seats.maxRow()
        val maxColumn = seats.maxColumn()

        seatsGridLayout.columnCount = maxColumn
        seatsGridLayout.rowCount = maxRow

        for (row in 0 until maxRow) {
            for (column in 0 until maxColumn) {
                val textView =
                    TextView(this).apply {
                        layoutParams =
                            GridLayout.LayoutParams().apply {
                                width = 0
                                height = 0
                                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                                rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                            }
                        setBackgroundResource(R.drawable.holder_seat_selector)
                        setOnClickListener {
                            presenter.selectSeat(position = Position(row, column), this)
                        }
                        gravity = Gravity.CENTER
                        text = "${'A' + row} ${column + 1}"
                    }
                seatsGridLayout.addView(textView)
            }
        }
    }

    override fun activateReservation(boolean: Boolean) {
        with(binding.btnSeatReservationComplete) {
            if (boolean) {
                isEnabled = true
                setBackgroundColor(getColor(R.color.complete_activated))
            } else {
                isEnabled = false
                setBackgroundColor(getColor(R.color.complete_deactivated))
            }
        }
    }

    override fun showCompleteReservation(
        reservationId: Int,
        theaterId: Int,
    ) {
        val alertDialog =
            AlertDialog.Builder(this)
                .setTitle(R.string.check_reservation_title)
                .setMessage(R.string.check_reservation_content)
                .setPositiveButton(R.string.check_reservation_complete) { _, _ ->
                    ReservationCompleteActivity.startActivity(this, reservationId, theaterId)
                }
                .setNegativeButton(R.string.check_reservation_cancel) { dialog, _ ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .create()

        alertDialog.show()
    }

    override fun showSeatReservationFail(throwable: Throwable) {
        showToast(throwable)
    }

    override fun showSelectedSeatFail(throwable: Throwable) {
        showToast(throwable)
    }

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
    }
}

fun interface OnReserveClickedListener {
    fun onClick()
}
