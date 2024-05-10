package woowacourse.movie.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReservationTicketRoomDao : ReservationTicketDao {
    @Insert
    override fun insert(reservationTicket: ReservationTicket): Long

    @Query("SELECT * from reservations WHERE id = :id")
    override fun findReservationById(id: Int): ReservationTicket

    @Query("SELECT * from reservations")
    override fun findAll(): List<ReservationTicket>
}
