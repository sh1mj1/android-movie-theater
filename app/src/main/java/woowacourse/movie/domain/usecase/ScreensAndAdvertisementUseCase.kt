package woowacourse.movie.domain.usecase

import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.repository.AdRepository
import woowacourse.movie.domain.repository.MovieRepository
import woowacourse.movie.domain.repository.ScreenRepository
import woowacourse.movie.ui.ScreenAd
import woowacourse.movie.ui.toPreviewUI

class ScreensAndAdvertisementUseCase(
    private val movieRepository: MovieRepository,
    private val screenRepository: ScreenRepository,
    private val adRepository: AdRepository,
) {
    fun generated(): List<ScreenAd> {
        val screenPreviewUis =
            screenRepository.load().map { screen ->
                val moviePoster = movieRepository.imageSrc(screen.movie.id)
                screen.toPreviewUI(image = moviePoster)
            }.toMutableList()

        val advertisement = adRepository.load()

        return generatedScreenAdList(screenPreviewUis, advertisement).toList()
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

    fun loadedScreens(screenId: Int): Result<Screen> = screenRepository.findById(screenId)
}
