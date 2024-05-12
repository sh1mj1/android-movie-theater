package woowacourse.movie.ui.seat

import woowacourse.movie.domain.model.Position
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.TimeReservation

interface SeatReservationContract {
    interface View {
        fun showTimeReservation(timeReservation: TimeReservation)

        fun showAllSeats(seats: Seats)

        fun showSelectedSeat(seatView: android.view.View)

        fun showDeselectedSeat(seatView: android.view.View)

        fun showTotalPrice(totalPrice: Int)

        fun activateReservation(activated: Boolean)

        fun checkReservationConfirm()

        fun showCompleteReservation(reservationTicketId: Int)

        fun showSeatReservationFail(throwable: Throwable)

        fun showSelectedSeatFail(throwable: Throwable)

        fun setAlarm(movieTimeMillis: Long)
    }

    interface Presenter {
        fun loadAllSeats()

        fun loadTimeReservation()

        fun selectSeat(
            position: Position,
            seatView: android.view.View,
        )

        fun deselectSeat(
            position: Position,
            seatView: android.view.View,
        )

        fun calculateTotalPrice()

        fun attemptReserve()

        fun reserve()

        fun setAlarm(reservationTicketId: Int)
    }
}
