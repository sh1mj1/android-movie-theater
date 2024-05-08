package woowacourse.movie.ui.home

import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Theaters

interface TheatersScreeningMovieContract {
    interface View {
        fun initTheaterAdapter(screen: Screen)

        fun showTheaters(
            screen: Screen,
            theaters: Theaters,
        )

        fun navigateToScreenDetail(
            screenId: Int,
            theaterId: Int,
        )
    }

    interface Presenter {
        fun initTheaterAdapter()

        fun loadTheaters()

        fun onTheaterSelected(theaterId: Int)
    }
}
