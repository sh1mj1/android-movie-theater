package woowacourse.movie.ui.home

import woowacourse.movie.domain.model.Screen
import woowacourse.movie.ui.ScreenAd

interface HomeContract {
    interface View {
        fun showScreens(screens: List<ScreenAd>)

        fun showTheatersScreeningMovie(screen: Screen)

        fun showTheatersFail(throwable: Throwable)
    }

    interface Presenter {
        fun loadScreen()

        fun loadTheaters(screenId: Int)
    }
}
