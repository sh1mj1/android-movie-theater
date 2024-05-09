package woowacourse.movie.ui.home

import woowacourse.movie.domain.repository.DummyAdvertisement
import woowacourse.movie.domain.repository.DummyMovies
import woowacourse.movie.domain.repository.DummyScreens
import woowacourse.movie.domain.usecase.ScreensAndAdvertisementUseCase

class HomePresenter(
    private val view: HomeContract.View,
    private val screensAndAdvertisementUseCase: ScreensAndAdvertisementUseCase =
        ScreensAndAdvertisementUseCase(
            movieRepository = DummyMovies(),
            screenRepository = DummyScreens(),
            adRepository = DummyAdvertisement(),
        ),
) : HomeContract.Presenter {
    override fun loadScreen() {
        val screenAdList = screensAndAdvertisementUseCase.generated()

        view.showScreens(screenAdList)
    }

    override fun loadTheaters(screenId: Int) {
        screensAndAdvertisementUseCase.loadedScreens(screenId)
            .onSuccess { screen ->
                view.showTheatersScreeningMovie(screen)
            }.onFailure { e ->
                view.showTheatersFail(e)
            }
    }
}
