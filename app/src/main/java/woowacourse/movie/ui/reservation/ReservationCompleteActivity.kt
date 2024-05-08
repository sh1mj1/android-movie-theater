package woowacourse.movie.ui.reservation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationCompleteBinding
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.repository.DummyReservation
import woowacourse.movie.domain.repository.DummyTheaters

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
        val reservationId = intent.getIntExtra(PUT_EXTRA_KEY_RESERVATION_ID, DEFAULT_RESERVATION_ID)
        val theaterId =
            intent.getIntExtra(PUT_EXTRA_THEATER_ID_KEY, DEFAULT_THEATER_ID)
        presenter = ReservationPresenter(this, DummyReservation, DummyTheaters(), theaterId, reservationId)
    }

    private fun initView() {
        presenter.loadReservation()
    }

    override fun showReservation(
        reservation: Reservation,
        theaterName: String,
    ) {
        binding.reservation = reservation
        binding.theaterName = theaterName
        binding.totalPrice = reservation.seats.totalPrice()
    }

    override fun showReservationFail(throwable: Throwable) {
        showToastMessage(throwable)
    }

    private fun showToastMessage(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val PUT_EXTRA_KEY_RESERVATION_ID = "reservationId"
        private const val PUT_EXTRA_THEATER_ID_KEY = "theaterId"
        private const val DEFAULT_RESERVATION_ID = -1
        private const val DEFAULT_THEATER_ID = -1

        fun startActivity(
            context: Context,
            reservationId: Int,
            theaterId: Int,
        ) {
            val intent = Intent(context, ReservationCompleteActivity::class.java)
            intent.putExtra(PUT_EXTRA_KEY_RESERVATION_ID, reservationId)
            intent.putExtra(PUT_EXTRA_THEATER_ID_KEY, theaterId)
            context.startActivity(intent)
        }
    }
}
