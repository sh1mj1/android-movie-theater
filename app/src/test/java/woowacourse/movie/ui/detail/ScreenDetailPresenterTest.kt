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
        // given
        presenter = testScreenDetailPresenterFixture(screenId = 1, theaterId = 1)

        // when
        presenter.loadScreen()

        // then
        verify(exactly = 1) { mockView.showScreen(fakeScreenDetailUI) }
        verify(exactly = 1) { mockView.showDateTimePicker(any(), any(), any(), any()) }
    }

    @Test
    fun `첫 화면에서 티켓의 개수는 1이다`() {
        // given
        presenter = testScreenDetailPresenterFixture()

        // when
        presenter.loadTicket()

        // then
        verify(exactly = 1) { mockView.showTicket(1) }
    }

    @Test
    fun `처음 화면에서 + 버튼을 누르면 티켓의 개수는 2이다`() {
        // given
        presenter = testScreenDetailPresenterFixture()

        // when
        presenter.plusTicket()

        // then
        verify(exactly = 1) { mockView.showTicket(2) }
    }

    @Test
    fun `처음 화면에서 - 버튼을 누르면 티켓의 개수는 1이다`() {
        // given
        presenter = testScreenDetailPresenterFixture()

        // when
        presenter.minusTicket()

        // then
        verify(exactly = 1) { mockView.showToastMessage(any()) }
    }

    @Test
    fun `티켓 개수가 10인 상태에서 + 버튼을 누르면 티켓의 개수는 늘어나지 않는다`() {
        // given
        presenter = testScreenDetailPresenterFixture()

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
        // givne
        presenter = testScreenDetailPresenterFixture(screenId = 1, theaterId = 1)

        // when
        presenter.reserve()

        // then
        verify(exactly = 1) { mockView.navigateToSeatsReservation(2, any()) }
    }

    private fun testScreenDetailPresenterFixture(
        screenId: Int = 1,
        theaterId: Int = 1,
    ): ScreenDetailPresenter {
        return ScreenDetailPresenter(
            view = mockView,
            movieRepository = FakeMovieRepository(),
            screenRepository = FakeScreenRepository(),
            reservationRepository = FakeReservationRepository(),
            screenId = screenId,
            theaterId = theaterId,
        )
    }
}
