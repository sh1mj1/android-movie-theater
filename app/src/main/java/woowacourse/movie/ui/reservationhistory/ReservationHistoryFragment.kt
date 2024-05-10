package woowacourse.movie.ui.reservationhistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.domain.model.Reservation

class ReservationHistoryFragment : Fragment(), ReservationHistoryContract.View {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_reservation_history, container, false)
    }

    override fun showAllReservationHistory(reservations: List<Reservation>) {
        TODO("Not yet implemented")
    }

    override fun showAllReservationHistoryError(throwable: Throwable) {
        TODO("Not yet implemented")
    }
}
