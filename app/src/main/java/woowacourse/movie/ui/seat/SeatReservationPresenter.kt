package woowacourse.movie.ui.seat

import android.view.View
import woowacourse.movie.domain.model.Position
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.TimeReservation
import woowacourse.movie.domain.repository.DummyTheaters
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.ScreenRepository
import woowacourse.movie.domain.repository.TheaterRepository
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.concurrent.thread

class SeatReservationPresenter(
    private val view: SeatReservationContract.View,
    private val screenRepository: ScreenRepository,
    private val reservationRepository: ReservationRepository,
    private val theaterRepository: TheaterRepository = DummyTheaters(),
    private val theaterId: Int,
    timeReservationId: Int,
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
        view.showTotalPrice(selectedSeats.totalPrice())
    }

    override fun selectSeat(
        position: Position,
        seatView: View,
    ) {
        val seat = loadedAllSeats.findSeat(position)

        if (selectedSeats.seats.size >= ticketCount) {
            view.showSelectedSeatFail(IllegalArgumentException("exceed ticket count that can be reserved."))
            return
        }
        selectedSeats = selectedSeats.add(seat)
        view.showSelectedSeat(seatView)
    }

    override fun deselectSeat(
        position: Position,
        seatView: View,
    ) {
        val seat = loadedAllSeats.findSeat(position)
        if (selectedSeats.seats.contains(seat)) {
            selectedSeats = selectedSeats.remove(seat)
        }
        view.showDeselectedSeat(seatView)
    }

    override fun calculateTotalPrice() {
        view.showTotalPrice(selectedSeats.totalPrice())
        if (selectedSeats.count() == ticketCount) {
            view.activateReservation(true)
        } else {
            view.activateReservation(false)
        }
    }

    override fun attemptReserve() {
        view.checkReservationConfirm()
    }

    override fun reserve() {
        val screenId = timeReservation.screen.id
        thread {
            reservationRepository.saveReservation(
                loadedScreen(screenId),
                selectedSeats,
                timeReservation.dateTime,
                theaterRepository.findById(theaterId),
            ).onSuccess { reservationTicketId ->
                setAlarm(reservationTicketId.toInt())
                view.showCompleteReservation(reservationTicketId.toInt())
            }.onFailure { e ->
                view.showSeatReservationFail(e)
            }
        }
    }

    override fun setAlarm(reservationTicketId: Int) {
        thread {
            reservationRepository.findById(reservationTicketId).onSuccess { reservationTicket ->
                val movieDateTime = LocalDateTime.of(reservationTicket.date, reservationTicket.time)
                val movieTimeMillis = movieDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                view.setAlarm(movieTimeMillis)
            }
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
