package woowacourse.movie.ui.home

import woowacourse.movie.domain.model.Screen
import woowacourse.movie.ui.ScreenAd

interface HomeContract {
    interface View {
        fun showScreens(screens: List<ScreenAd>)

        fun showTheatersBottomSheet(screen: Screen)
    }

    interface Presenter {
        fun loadScreen()

        fun loadTheaters(screenId: Int)
    }
}
