package woowacourse.movie.ui.home

import woowacourse.movie.domain.model.TheaterScreeningCount

interface TheatersScreeningMovieContract {
    interface View {
        fun showTheatersScreeningcount(theaterScreeningCount: List<TheaterScreeningCount>)

        fun showScreenDetail(
            screenId: Int,
            theaterId: Int,
        )
    }

    interface Presenter {
        fun loadTheaters()

        fun selectTheater(theaterId: Int)
    }
}
