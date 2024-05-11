package woowacourse.movie.ui.reservation

import android.os.Handler
import android.os.Looper
import woowacourse.movie.domain.repository.ReservationRepository
import kotlin.concurrent.thread

class ReservationPresenter(
    private val view: ReservationContract.View,
    private val reservationRepository: ReservationRepository,
    private val reservationTicketId: Int = 1,
) : ReservationContract.Presenter {
    private val uiHandler = Handler(Looper.getMainLooper())

    override fun loadReservation() {
        thread {
            reservationRepository.findById(reservationTicketId)
                .onSuccess { reservationTicket ->
                    uiHandler.post {
                        view.showReservation(reservationTicket)
                    }
                }
                .onFailure { e ->
                    view.showReservationFail(e)
                }
        }
    }
}
