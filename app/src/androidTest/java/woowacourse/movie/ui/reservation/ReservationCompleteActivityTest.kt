package woowacourse.movie.ui.reservation

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.R
import woowacourse.movie.domain.model.DateTime
import woowacourse.movie.domain.model.Grade
import woowacourse.movie.domain.model.Position
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.domain.repository.DummyReservation
import java.time.LocalDate
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
class ReservationCompleteActivityTest {
    @get:Rule
    val activityRule: ActivityScenarioRule<ReservationCompleteActivity> =
        ActivityScenarioRule<ReservationCompleteActivity>(
            Intent(ApplicationProvider.getApplicationContext(), ReservationCompleteActivity::class.java).apply {
                putExtra("reservationId", testFixtureReservationId())
                putExtra("theaterId", 1)
            },
        )

    @Test
    fun `예약한_영화의_제목을_표시한다`() {
        onView(withId(R.id.tv_reservation_title)).check(matches(withText("")))
    }

    @Test
    fun `예약한_영화의_러닝타임을_표시한다`() {
        onView(withId(R.id.tv_reservation_date)).check(matches(withText("2024-03-01")))
    }

    @Test
    fun `상영_영화_예매_수를_표시한다`() {
        onView(withId(R.id.tv_reservation_count)).check(matches(withText("일반 2명 |")))
    }

    @Test
    fun `상영_영화_예매_가격을_표시한다`() {
        onView(withId(R.id.tv_reservation_amount)).check(matches(withText("27,000원(현장 결제)")))
    }

    @Test
    fun showTheReservedSeats() {
        onView(withId(R.id.tv_reservation_seats)).check(matches(withText("A1,B2 |")))
    }

    @Test
    fun showTimeOfReservation() {
        onView(withId(R.id.tv_reservation_time)).check(matches(withText("10:00")))
    }

    private fun testFixtureReservationId(): Long {
        return DummyReservation.saveReservation(
            Screen.NULL,
            Seats(
                Seat(Position(0, 0), Grade.S),
                Seat(Position(1, 1), Grade.A),
            ),
            DateTime(LocalDate.of(2024, 3, 1), LocalTime.of(10, 0)),
            Theater.NULL,
        ).getOrThrow()
    }
}
