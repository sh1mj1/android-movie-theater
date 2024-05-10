package woowacourse.movie.data

class FakeReservationTicketDao : ReservationTicketRoomDao {
    private val reservationTickets = mutableListOf<ReservationTicket>()

    override fun insert(reservationTicket: ReservationTicket): Long {
        val ticket = reservationTicket.copy(id = reservationTickets.size + 1)
        reservationTickets.add(ticket)
        return reservationTickets.size.toLong()
    }

    override fun findReservationById(id: Int): ReservationTicket =
        reservationTickets.find { it.id == id }
            ?: throw NoSuchElementException("ReservationTicket not found with id: $id.")

    override fun findAll(): List<ReservationTicket> = reservationTickets.toList()
}
