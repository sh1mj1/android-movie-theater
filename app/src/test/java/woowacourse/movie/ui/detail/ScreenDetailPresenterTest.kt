package woowacourse.movie.ui.detail

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.domain.model.Image
import woowacourse.movie.domain.repository.FakeMovieRepository
import woowacourse.movie.domain.repository.FakeReservationRepository
import woowacourse.movie.domain.repository.FakeScreenRepository
import woowacourse.movie.ui.MovieDetailUI
import woowacourse.movie.ui.ScreenDetailUi
import java.time.LocalDate

class ScreenDetailPresenterTest {
    private lateinit var mockView: ScreenDetailContract.View
    private lateinit var presenter: ScreenDetailContract.Presenter

    @BeforeEach
    fun setUp() {
        mockView = mockk<ScreenDetailContract.View>(relaxed = true)
        presenter =
            ScreenDetailPresenter(
                view = mockView,
                movieRepository = FakeMovieRepository(),
                screenRepository = FakeScreenRepository(),
                reservationRepository = FakeReservationRepository(),
            )
    }

    private val fakeScreenDetailUI =
        ScreenDetailUi(
            id = 1,
            movieDetailUI =
                MovieDetailUI(
                    title = "title1",
                    runningTime = 1,
                    description = "description1",
                    image = Image.StringImage("1"),
                ),
            dateRange = DateRange(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 3)),
        )

    @Test
    fun `영화 정보를 표시`() {
        // when
        presenter.saveId(screenId = 1, theaterId = 1)
        presenter.loadScreen()

        // then
        verify(exactly = 1) { mockView.showScreen(fakeScreenDetailUI) }
        verify(exactly = 1) { mockView.showDateTimePicker(any(), any(), any(), any()) }
    }

    @Test
    fun `첫 화면에서 티켓의 개수는 1이다`() {
        // when
        presenter.loadTicket()

        // then
        verify(exactly = 1) { mockView.showTicket(1) }
    }

    @Test
    fun `처음 화면에서 + 버튼을 누르면 티켓의 개수는 2이다`() {
        // when
        presenter.plusTicket()

        // then
        verify(exactly = 1) { mockView.showTicket(2) }
    }

    @Test
    fun `처음 화면에서 - 버튼을 누르면 티켓의 개수는 1이다`() {
        // when
        presenter.minusTicket()

        // then
        verify(exactly = 1) { mockView.showToastMessage(any()) }
    }

    @Test
    fun `티켓 개수가 10인 상태에서 + 버튼을 누르면 티켓의 개수는 늘어나지 않는다`() {
        repeat(9) {
            presenter.plusTicket()
        }
        // when
        presenter.plusTicket()

        // then
        verify(exactly = 1) { mockView.showToastMessage(any()) }
    }

    @Test
    fun `reserve with date, time and ticket count`() {
        // when
        presenter.saveId(1, 1)
        presenter.reserve()

        // then
        verify(exactly = 1) { mockView.navigateToSeatsReservation(2, any()) }
    }
}
