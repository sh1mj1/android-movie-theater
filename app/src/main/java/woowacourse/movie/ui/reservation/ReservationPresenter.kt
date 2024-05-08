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
                when (e) {
                    is NoSuchElementException -> view.goToBack("해당하는 상영 정보가 없습니다.")
                    else -> view.unexpectedFinish("예상치 못한 에러가 발생했습니다")
                }
            }
    }
}
