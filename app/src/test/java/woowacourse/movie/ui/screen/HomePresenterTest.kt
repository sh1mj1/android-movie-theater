package woowacourse.movie.ui.screen

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.repository.FakeMovieDataSource
import woowacourse.movie.data.repository.FakeScreenDataSource
import woowacourse.movie.data.source.DummyAdvertisementDataSource
import woowacourse.movie.domain.usecase.ScreensAndAdvertisementUseCase
import woowacourse.movie.ui.home.HomeContract
import woowacourse.movie.ui.home.HomePresenter

class HomePresenterTest {
    private lateinit var view: HomeContract.View
    private lateinit var presenter: HomeContract.Presenter

    @BeforeEach
    fun setUp() {
        view = mockk<HomeContract.View>(relaxed = true)
        presenter =
            HomePresenter(
                view = view,
                ScreensAndAdvertisementUseCase(
                    movieDataSource = FakeMovieDataSource(),
                    screenDataSource = FakeScreenDataSource(),
                    advertisementDataSource = DummyAdvertisementDataSource(),
                ),
            )
    }

    @Test
    fun `영화들을 보여준다`() {
        presenter.loadScreen()

        verify { view.showScreens(any()) }
    }

    @Test
    fun `영화를 상영하는 상영관들을 보여준다`() {
        presenter.loadTheaters(1)

        verify { view.showTheatersScreeningMovie(any()) }
    }
}
