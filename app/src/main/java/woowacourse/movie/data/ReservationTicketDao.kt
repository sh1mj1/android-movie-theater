package woowacourse.movie.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReservationTicketDao {
    @Insert
    fun insert(reservationTicket: ReservationTicket)

    @Query("SELECT * from reservations WHERE id = :id")
    fun findReservationById(id: Int): ReservationTicket

    @Query("SELECT * from reservations")
    fun findAll(): List<ReservationTicket>
}
