package woowacourse.movie.ui.home

import woowacourse.movie.domain.repository.DummyAdvertisement
import woowacourse.movie.domain.repository.MovieRepository
import woowacourse.movie.domain.repository.ScreenRepository
import woowacourse.movie.ui.ScreenAd
import woowacourse.movie.ui.toPreviewUI

class HomePresenter(
    private val view: HomeContract.View,
    private val movieRepository: MovieRepository,
    private val screenRepository: ScreenRepository,
    private val adRepository: DummyAdvertisement = DummyAdvertisement(),
) : HomeContract.Presenter {
    override fun loadScreen() {
        val screenPreviewUis =
            screenRepository.load().map { screen ->
                val moviePoster = movieRepository.imageSrc(screen.movie.id)
                screen.toPreviewUI(image = moviePoster)
            }.toMutableList()

        val advertisement = adRepository.load()

        val screenAdList = generatedScreenAdList(screenPreviewUis, advertisement)
        view.showScreens(screenAdList)
    }

    private fun generatedScreenAdList(
        screenPreviewUis: MutableList<ScreenAd.ScreenPreviewUi>,
        advertisement: ScreenAd.Advertisement,
    ): MutableList<ScreenAd> {
        val totalItems = screenPreviewUis.size + screenPreviewUis.size / 3
        val screenAdList = mutableListOf<ScreenAd>()

        (0 until totalItems).mapIndexed { index: Int, _ ->
            if ((index + 1) % 4 == 0) {
                screenAdList.add(advertisement)
            } else {
                val screenIndex = index - index / 4
                if (screenIndex < screenPreviewUis.size) {
                    screenAdList.add(screenPreviewUis[screenIndex])
                } else {
                }
            }
        }
        return screenAdList
    }

    override fun loadTheaters(screenId: Int) {
        val screen = screenRepository.findById(screenId).getOrThrow()
        view.showTheatersScreeningMovie(screen)
    }
}
