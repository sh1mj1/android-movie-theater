package woowacourse.movie.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.domain.model.DateTime
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.Ticket
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "reservations")
data class ReservationTicket(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val screen: Screen,
    val date: LocalDate,
    val time: LocalTime,
    val seats: Seats,
    val theaterName: String,
) {
    companion object {
        val NULL =
            ReservationTicket(
                screen = Screen.NULL,
                date = LocalDate.MIN,
                time = LocalTime.MIN,
                seats = Seats(),
                theaterName = "",
            )
    }
}

fun ReservationTicket.toReservation(): Reservation {
    return Reservation(
        id = id,
        screen = screen,
        ticket = Ticket(seats.count()),
        seats = seats,
        dateTime = DateTime(date, time),
    )
}
