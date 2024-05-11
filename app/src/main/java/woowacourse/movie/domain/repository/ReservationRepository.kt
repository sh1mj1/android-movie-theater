package woowacourse.movie.domain.repository

import woowacourse.movie.data.ReservationTicket
import woowacourse.movie.domain.model.DateTime
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.domain.model.TimeReservation

interface ReservationRepository {
    fun saveReservation(
        screen: Screen,
        seats: Seats,
        dateTime: DateTime,
        theater: Theater,
    ): Result<Long>

    fun saveTimeReservation(
        screen: Screen,
        count: Int,
        dateTime: DateTime,
    ): Result<Int>

    fun loadAllReservationHistory(): Result<List<ReservationTicket>>

    fun loadTimeReservation(timeReservationId: Int): TimeReservation

    fun findById(id: Int): Result<Reservation>
}
