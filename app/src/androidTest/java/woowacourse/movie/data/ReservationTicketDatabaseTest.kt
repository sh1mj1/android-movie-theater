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
    fun testInsert() {
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

        val insertedReservationTicketId = dao.insert(reservationTicket)

        assertEquals(1, insertedReservationTicketId)
    }

    @Test
    fun testFindAllReservationTickets() {
        val movie1 = Movie(1, "title1", 120, "description1")
        val movie2 = Movie(2, "title2", 120, "description2")
        val movie3 = Movie(3, "title3", 120, "description3")

        val screen1 = Screen(1, movie1, DateRange(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 12, 31)))
        val screen2 = Screen(2, movie2, DateRange(LocalDate.of(2022, 2, 1), LocalDate.of(2022, 3, 10)))
        val screen3 = Screen(3, movie3, DateRange(LocalDate.of(2023, 2, 10), LocalDate.of(2023, 10, 7)))

        val seats1 =
            Seats(
                Seat(Position(1, 1), Grade.A),
                Seat(Position(3, 2), Grade.B),
                Seat(Position(1, 3), Grade.S),
            )

        val seats2 =
            Seats(
                Seat(Position(2, 1), Grade.A),
                Seat(Position(1, 2), Grade.B),
                Seat(Position(3, 3), Grade.S),
            )

        val seats3 =
            Seats(
                Seat(Position(3, 1), Grade.A),
                Seat(Position(2, 2), Grade.B),
                Seat(Position(1, 3), Grade.S),
            )

        val reservationTicket1 =
            ReservationTicket(
                id = 1,
                screen = screen1,
                date = LocalDate.of(2024, 3, 2),
                time = LocalTime.of(12, 30),
                seats = seats1,
                theaterName = "theater1",
            )
        val reservationTicket2 =
            ReservationTicket(
                id = 2,
                screen = screen2,
                date = LocalDate.of(2024, 3, 2),
                time = LocalTime.of(12, 30),
                seats = seats2,
                theaterName = "theater2",
            )
        val reservationTicket3 =
            ReservationTicket(
                id = 3,
                screen = screen3,
                date = LocalDate.of(2024, 3, 2),
                time = LocalTime.of(12, 30),
                seats = seats3,
                theaterName = "theater3",
            )

        dao.insert(reservationTicket1)
        dao.insert(reservationTicket2)
        dao.insert(reservationTicket3)

        val foundAllReservationTickets = dao.findAll()
        val expected = listOf(reservationTicket1, reservationTicket2, reservationTicket3)

        assertEquals(expected, foundAllReservationTickets)
    }
}
