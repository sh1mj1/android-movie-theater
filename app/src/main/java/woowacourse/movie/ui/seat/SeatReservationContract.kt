package woowacourse.movie.ui.seat

import woowacourse.movie.domain.model.Position
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.TimeReservation

interface SeatReservationContract {
    interface View {
        fun showTimeReservation(timeReservation: TimeReservation)

        fun showAllSeats(seats: Seats)

        fun showSelectedSeat(seat: Seat)

        fun showDeselectedSeat(seat: Seat)

        fun showTotalPrice(totalPrice: Int)

        fun activateReservation(activated: Boolean)

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

        fun selectSeat(position: Position)

        fun deselectSeat(position: Position)

        fun calculateTotalPrice()

        fun reserve()
    }
}
