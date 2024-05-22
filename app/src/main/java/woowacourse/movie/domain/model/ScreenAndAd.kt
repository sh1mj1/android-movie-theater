package woowacourse.movie.domain.model

sealed interface ScreenAndAd {
    data class Screen(
        val id: Int,
        val movie: Movie,
        val dateRange: DateRange,
    ) : ScreenAndAd

    data class Advertisement(
        val id: Int,
        val content: String,
        val image: Image<Any>,
    ) : ScreenAndAd
}

// fun ScreenData.toScreen(): ScreenAndAd.Screen =
//    ScreenAndAd.Screen(
//        id = id,
//        movie = movie,
//        dateRange = dateRange,
//    )
