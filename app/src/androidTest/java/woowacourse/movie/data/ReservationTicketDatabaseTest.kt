package woowacourse.movie.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.domain.model.Grade
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.Position
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.Seats
import java.time.LocalDate
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
class ReservationTicketDatabaseTest {
    private lateinit var dao: ReservationTicketDao
    private lateinit var db: ReservationTicketDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, ReservationTicketDatabase::class.java).build()
        dao = db.reservationDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun testInsertAndRetrieve() {
        val movie = Movie(1, "title", 120, "description")
        val screen = Screen(1, movie, DateRange(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 12, 31)))
        val seats =
            Seats(
                Seat(Position(1, 1), Grade.A),
            )
        val reservationTicket =
            ReservationTicket(
                id = 1,
                screen = screen,
                date = LocalDate.of(2024, 3, 2),
                time = LocalTime.of(12, 30),
                seats = seats,
                theaterName = "theater1",
            )

        dao.insert(reservationTicket)

        assertEquals(reservationTicket, dao.findReservationById(1))
    }
}
