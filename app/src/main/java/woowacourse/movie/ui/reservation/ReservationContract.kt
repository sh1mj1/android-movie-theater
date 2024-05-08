package woowacourse.movie.ui.reservation

import woowacourse.movie.domain.model.Reservation

interface ReservationContract {
    interface View {
        fun showReservation(
            reservation: Reservation,
            theaterName: String,
        )

        fun showReservationFail(throwable: Throwable)
    }

    interface Presenter {
        fun loadReservation()
    }
}
