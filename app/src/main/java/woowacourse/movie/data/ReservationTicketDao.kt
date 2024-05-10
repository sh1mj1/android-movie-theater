package woowacourse.movie.data

interface ReservationTicketDao {
    fun insert(reservationTicket: ReservationTicket): Long

    fun findReservationById(id: Int): ReservationTicket

    fun findAll(): List<ReservationTicket>
}
