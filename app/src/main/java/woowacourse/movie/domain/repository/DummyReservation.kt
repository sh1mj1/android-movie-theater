package woowacourse.movie.domain.repository

import woowacourse.movie.data.ReservationTicket
import woowacourse.movie.domain.model.DateTime
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.TimeReservation

object DummyReservation : ReservationRepository {
    private val timeReservation = mutableListOf<TimeReservation>()

    private val reservations = mutableListOf<Reservation>()

    override fun saveReservation(
        screen: Screen,
        seats: Seats,
        dateTime: DateTime,
        theater: Theater,
    ): Result<Long> =
        runCatching {
            val id = reservations.size + 1
            reservations.add(Reservation(id, screen, Ticket(seats.count()), seats, dateTime))
            id.toLong()
        }

    override fun saveTimeReservation(
        screen: Screen,
        count: Int,
        dateTime: DateTime,
    ): Result<Int> =
        runCatching {
            val id = timeReservation.size + 1
            timeReservation.add(TimeReservation(id, screen, Ticket(count), dateTime))
            id
        }

    override fun loadAllReservationHistory(): Result<List<ReservationTicket>> {
        TODO("Not yet implemented")
    }

    override fun loadTimeReservation(timeReservationId: Int): TimeReservation =
        timeReservation.find {
            it.id == timeReservationId
        } ?: throw NoSuchElementException("TimeReservation not found with timeReservationId: $timeReservationId.")

    override fun findById(id: Int): Result<Reservation> {
        return runCatching {
            val reservation = reservations.find { it.id == id }
            reservation ?: throw IllegalArgumentException("예약 정보를 찾을 수 없습니다.")
        }
    }
}
