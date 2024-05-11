package woowacourse.movie.domain.repository

import woowacourse.movie.data.ReservationTicket
import woowacourse.movie.data.ReservationTicketDao
import woowacourse.movie.data.toReservation
import woowacourse.movie.domain.model.DateTime
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.TimeReservation

class OfflineReservationRepository(private val reservationTicketDao: ReservationTicketDao) : ReservationRepository {
    override fun saveReservation(
        screen: Screen,
        seats: Seats,
        dateTime: DateTime,
        theater: Theater,
    ): Result<Long> {
        return runCatching {
            val id =
                reservationTicketDao.insert(
                    reservationTicket =
                        ReservationTicket(
                            screen = screen,
                            date = dateTime.date,
                            time = dateTime.time,
                            seats = seats,
                            theater = theater,
                        ),
                )
            id
        }
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

    override fun loadAllReservationHistory(): Result<List<ReservationTicket>> =
        runCatching {
            reservationTicketDao.findAll()
        }

    override fun loadTimeReservation(timeReservationId: Int): TimeReservation =
        timeReservation.find {
            it.id == timeReservationId
        } ?: throw NoSuchElementException("TimeReservation not found with timeReservationId: $timeReservationId.")

    override fun findById(id: Int): Result<Reservation> =
        runCatching {
            reservationTicketDao.findReservationById(id).toReservation()
        }

    companion object {
        const val TAG = "OfflineReservationRepository"
        private val timeReservation = mutableListOf<TimeReservation>()
    }
}
