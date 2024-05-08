package woowacourse.movie.ui.seat

import android.view.View
import woowacourse.movie.domain.model.Position
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.TimeReservation
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.ScreenRepository

class SeatReservationPresenter(
    private val view: SeatReservationContract.View,
    private val screenRepository: ScreenRepository,
    private val reservationRepository: ReservationRepository,
    private val theaterId: Int,
    private val timeReservationId: Int,
) : SeatReservationContract.Presenter {
    private val timeReservation: TimeReservation = reservationRepository.loadTimeReservation(timeReservationId)
    private val loadedAllSeats: Seats = screenRepository.seats(timeReservation.screen.id)
    private val ticketCount = timeReservation.ticket.count

    private var selectedSeats = Seats()

    override fun loadAllSeats() {
        view.showAllSeats(loadedAllSeats)
    }

    override fun loadTimeReservation() {
        view.showTimeReservation(timeReservation)
    }

    override fun selectSeat(
        position: Position,
        seatView: View,
    ) {
        val seat = loadedAllSeats.findSeat(position)

        if (toggleSeatSelection(seat, seatView)) {
            view.activateReservation(selectedSeats.count() == ticketCount)
            view.showTotalPrice(selectedSeats.totalPrice())
        }
    }

    private fun toggleSeatSelection(
        seat: Seat,
        seatView: View,
    ): Boolean =
        when {
            selectedSeats.seats.contains(seat) -> {
                deselectSeat(seatView, seat)
                true
            }

            selectedSeats.seats.size < ticketCount -> {
                selectSeat(seatView, seat)
                true
            }

            else -> {
                view.showSelectedSeatFail(IllegalArgumentException("exceed ticket count that can be reserved."))
                false
            }
        }

    private fun selectSeat(
        seatView: View,
        seat: Seat,
    ) {
        seatView.isSelected = true
        selectedSeats = selectedSeats.add(seat)
    }

    private fun deselectSeat(
        seatView: View,
        seat: Seat,
    ) {
        seatView.isSelected = false
        selectedSeats = selectedSeats.remove(seat)
    }

    override fun reserve() {
        val screenId = timeReservation.screen.id

        reservationRepository.save(
            loadedScreen(screenId),
            selectedSeats,
            timeReservation.dateTime,
        ).onSuccess { reservationId ->
            view.showCompleteReservation(reservationId, theaterId)
        }.onFailure { e ->
            view.showSeatReservationFail(e)
        }
    }

    private fun loadedScreen(screenId: Int): Screen {
        screenRepository.findById(id = screenId).onSuccess { screen ->
            return screen
        }.onFailure { e ->
            throw e
        }
        throw IllegalStateException("예기치 못한 오류")
    }
}
