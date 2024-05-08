package woowacourse.movie.ui.reservation

import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.TheaterRepository

class ReservationPresenter(
    private val view: ReservationContract.View,
    private val reservationRepository: ReservationRepository,
    private val theaterRepository: TheaterRepository,
    private val theaterId: Int,
    private val reservationId: Int,
) : ReservationContract.Presenter {
    override fun loadReservation() {
        reservationRepository.findById(reservationId)
            .onSuccess {
                val theaterName = theaterRepository.findById(theaterId).name

                view.showReservation(it, theaterName)
            }
            .onFailure { e ->
                view.showReservationFail(e)
            }
    }
}
