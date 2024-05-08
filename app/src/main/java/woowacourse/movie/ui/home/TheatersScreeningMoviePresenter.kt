package woowacourse.movie.ui.home

import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.repository.ScreenRepository
import woowacourse.movie.domain.repository.TheaterRepository

class TheatersScreeningMoviePresenter(
    private val view: TheatersScreeningMovieContract.View,
    private val screenRepository: ScreenRepository,
    private val theaterRepository: TheaterRepository,
    private val screenId: Int,
) : TheatersScreeningMovieContract.Presenter {
    private val loadedScreen: Screen = loadedScreen()

    override fun initTheaterAdapter() {
        view.initTheaterAdapter(loadedScreen)
    }

    override fun loadTheaters() {
        val theaters = theaterRepository.loadAll()

        view.showTheaters(loadedScreen, theaters.screeningTheater(loadedScreen.movie))
    }

    private fun loadedScreen() = screenRepository.findById(screenId).getOrThrow()

    override fun onTheaterSelected(theaterId: Int) {
        view.showScreenDetail(screenId, theaterId)
    }
}
