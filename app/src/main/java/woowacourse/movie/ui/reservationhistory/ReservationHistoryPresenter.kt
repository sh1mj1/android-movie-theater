package woowacourse.movie.ui.reservationhistory

import woowacourse.movie.domain.repository.ReservationRepository

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
    private val reservationRepository: ReservationRepository,
) : ReservationHistoryContract.Presenter {
    override fun loadAllReservationHistory() {
        reservationRepository.loadAllReservationHistory().onSuccess { reservations ->
            view.showAllReservationHistory(reservations)
        }.onFailure { e ->
            view.showAllReservationHistoryError(e)
        }
    }
}
