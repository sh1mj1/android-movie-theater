package woowacourse.movie.ui.seat

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isNotSelected
import androidx.test.espresso.matcher.ViewMatchers.isSelected
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.data.ReservationTicketDatabase
import woowacourse.movie.domain.model.DateTime
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.repository.OfflineReservationRepository

@RunWith(AndroidJUnit4::class)
class SeatReservationActivityTest {
    private lateinit var scenario: ActivityScenario<SeatReservationActivity>

    @Before
    fun setup() {
        // 상영작, 예매 개수, 날짜를 미리 예약해둔다.
        OfflineReservationRepository(reservationTicketDao = dao).saveTimeReservation(
            screen = Screen.NULL,
            count = 2,
            dateTime = DateTime.NULL,
        )

        scenario =
            ActivityScenario.launch(
                Intent(ApplicationProvider.getApplicationContext(), SeatReservationActivity::class.java).apply {
                    putExtra("timeReservationId", 1)
                    putExtra("theaterId", 1)
                },
            )
    }

    @Test
    fun the_View_Is_Selected_When_Seats_Are_Selected() {
        // given
        val a1 = onView(withText("A 1"))
        val b2 = onView(withText("B 2"))
        val c3 = onView(withText("C 3"))

        // when
        a1.perform(click())
        b2.perform(click())

        // then
        a1.check((matches(isSelected())))
        b2.check((matches(isSelected())))
        c3.check((matches(isNotSelected())))
    }

    companion object {
        private val db = ReservationTicketDatabase.getDatabase(ApplicationProvider.getApplicationContext())
        private val dao = db.reservationDao()
    }
}
