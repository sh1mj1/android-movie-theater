package woowacourse.movie.ui.seat

import woowacourse.movie.domain.model.Position
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.TimeReservation

interface SeatReservationContract {
    interface View {
        fun showTimeReservation(timeReservation: TimeReservation)

        fun showAllSeats(seats: Seats)

        fun showTotalPrice(totalPrice: Int)

        fun activateReservation(boolean: Boolean)

        fun showCompleteReservation(
            reservationId: Int,
            theaterId: Int,
        )

        fun showSeatReservationFail(throwable: Throwable)

        fun showSelectedSeatFail(throwable: Throwable)
    }

    interface Presenter {
        fun loadAllSeats()

        fun loadTimeReservation()

        fun selectSeat(
            position: Position,
            seatView: android.view.View,
        )

        fun reserve()
    }
}
