package woowacourse.movie.ui.reservation

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.data.ReservationTicket
import woowacourse.movie.data.ReservationTicketDatabase
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
class ReservationCompleteActivityTest {
    @get:Rule
    val activityRule: ActivityScenarioRule<ReservationCompleteActivity> =
        ActivityScenarioRule<ReservationCompleteActivity>(
            Intent(ApplicationProvider.getApplicationContext(), ReservationCompleteActivity::class.java).apply {
                putExtra("reservationId", 1)
                putExtra("theaterId", 1)
            },
        )

    @Test
    fun `예약한_영화의_제목을_표시한다`() {
        onView(withId(R.id.tv_reservation_title)).check(matches(withText("title")))
    }

    @Test
    fun `예약한_영화의_상영_날짜를_표시한다`() {
        onView(withId(R.id.tv_reservation_date)).check(matches(withText("2024-03-02")))
    }

    @Test
    fun `상영_영화_예매_수를_표시한다`() {
        onView(withId(R.id.tv_reservation_count)).check(matches(withText("일반 1명 |")))
    }

    @Test
    fun `상영_영화_예매_가격을_표시한다`() {
        onView(withId(R.id.tv_reservation_amount)).check(matches(withText("12,000원(현장 결제)")))
    }

    @Test
    fun showTheReservedSeats() {
        onView(withId(R.id.tv_reservation_seats)).check(matches(withText("B2 |")))
    }

    @Test
    fun showTimeOfReservation() {
        onView(withId(R.id.tv_reservation_time)).check(matches(withText("12:30")))
    }

    companion object {
        private val db = ReservationTicketDatabase.getDatabase(ApplicationProvider.getApplicationContext())
        private val dao = db.reservationDao()

        @BeforeClass
        @JvmStatic
        fun setupOnlyOnce() {
            dao.insert(reservationTicketTestFixture())
        }

        @AfterClass
        @JvmStatic
        fun tearDown() {
            db.close()
        }
    }
}

private fun reservationTicketTestFixture(): ReservationTicket {
    val movie = Movie(1, "title", 120, "description")
    val screen = Screen(1, movie, DateRange(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 12, 31)))
    val seats =
        Seats(
            Seat(Position(1, 1), Grade.A),
        )
    return ReservationTicket(
        screen = screen,
        date = LocalDate.of(2024, 3, 2),
        time = LocalTime.of(12, 30),
        seats = seats,
        theaterName = "theater1",
    )
}
