package woowacourse.movie.ui.reservationhistory

import woowacourse.movie.domain.model.Reservation

interface ReservationHistoryContract {
    interface View {
        fun showAllReservationHistory(reservations: List<Reservation>)

        fun showAllReservationHistoryError(throwable: Throwable)
    }

    interface Presenter {
        fun loadAllReservationHistory()
    }
}
