package woowacourse.movie.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Seats
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "reservations")
data class ReservationTicket(
    @PrimaryKey(autoGenerate = true)
    val id: Int = -1,
    val screen: Screen,
    val date: LocalDate,
    val time: LocalTime,
    val seats: Seats,
    val theaterName: String,
)
