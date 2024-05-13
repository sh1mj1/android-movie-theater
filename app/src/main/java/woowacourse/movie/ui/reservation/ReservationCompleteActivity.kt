package woowacourse.movie.ui.reservation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.data.ReservationTicket
import woowacourse.movie.data.ReservationTicketDatabase
import woowacourse.movie.databinding.ActivityReservationCompleteBinding
import woowacourse.movie.domain.repository.OfflineReservationRepository

class ReservationCompleteActivity : AppCompatActivity(), ReservationContract.View {
    private lateinit var presenter: ReservationContract.Presenter
    private val binding: ActivityReservationCompleteBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_reservation_complete)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        initView()
    }

    private fun initPresenter() {
        val reservationTicketId = intent.getIntExtra(PUT_EXTRA_KEY_RESERVATION_TICKET_ID, DEFAULT_RESERVATION_TICKET_ID)

        presenter =
            ReservationPresenter(
                this,
                OfflineReservationRepository(
                    ReservationTicketDatabase.getDatabase(applicationContext).reservationDao(),
                ),
                reservationTicketId,
            )
    }

    private fun initView() {
        presenter.loadReservation()
    }

    override fun showReservation(reservationTicket: ReservationTicket) {
        binding.reservationTicket = reservationTicket
        binding.totalPrice = reservationTicket.totalPrice()
    }

    override fun showReservationFail(throwable: Throwable) {
        showToastMessage(throwable)
    }

    private fun showToastMessage(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val PUT_EXTRA_KEY_RESERVATION_TICKET_ID = "reservationTicketId"
        const val DEFAULT_RESERVATION_TICKET_ID = -1

        fun startActivity(
            context: Context,
            reservationTicketId: Int,
        ) {
            val intent = Intent(context, ReservationCompleteActivity::class.java)
            intent.putExtra(PUT_EXTRA_KEY_RESERVATION_TICKET_ID, reservationTicketId)
            context.startActivity(intent)
        }
    }
}
