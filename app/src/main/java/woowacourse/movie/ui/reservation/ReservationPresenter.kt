package woowacourse.movie.ui.reservation

import android.os.Handler
import android.os.Looper
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.TheaterRepository
import kotlin.concurrent.thread

class ReservationPresenter(
    private val view: ReservationContract.View,
    private val reservationRepository: ReservationRepository,
    private val theaterRepository: TheaterRepository,
    private val theaterId: Int,
    private val reservationId: Int,
) : ReservationContract.Presenter {
    private val uiHandler = Handler(Looper.getMainLooper())

    override fun loadReservation() {
        thread {
            reservationRepository.findById(reservationId)
                .onSuccess {
                    val theaterName = theaterRepository.findById(theaterId).name
                    uiHandler.post {
                        view.showReservation(it, theaterName)
                    }
                }
                .onFailure { e ->
                    uiHandler.post {
                        view.showReservationFail(e)
                    }
                }
        }
    }
}
