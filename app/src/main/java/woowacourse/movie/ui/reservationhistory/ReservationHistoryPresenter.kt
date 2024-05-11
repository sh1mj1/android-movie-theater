package woowacourse.movie.ui.reservationhistory

import android.os.Handler
import android.os.Looper
import woowacourse.movie.domain.repository.ReservationRepository
import kotlin.concurrent.thread

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
    private val reservationRepository: ReservationRepository,
) : ReservationHistoryContract.Presenter {
    private val uiHandler: Handler = Handler(Looper.getMainLooper())

    override fun loadAllReservationHistory() {
        thread {
            reservationRepository.loadAllReservationHistory().onSuccess { reservationTickets ->
                uiHandler.post {
                    view.showAllReservationHistory(reservationTickets)
                }
            }.onFailure { e ->
                uiHandler.post {
                    view.showAllReservationHistoryError(e)
                }
            }
        }
    }
}
