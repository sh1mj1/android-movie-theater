package woowacourse.movie.ui.reservationhistory

import woowacourse.movie.data.ReservationTicket

interface ReservationHistoryContract {
    interface View {
        fun showAllReservationHistory(reservations: List<ReservationTicket>)

        fun showAllReservationHistoryError(throwable: Throwable)
    }

    interface Presenter {
        fun loadAllReservationHistory()
    }
}
