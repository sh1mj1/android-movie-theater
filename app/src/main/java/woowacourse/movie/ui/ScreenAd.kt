package woowacourse.movie.ui

import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.domain.model.Image
import woowacourse.movie.domain.model.Screen

sealed interface ScreenAd {
    data class ScreenPreviewUi(
        val id: Int,
        val moviePreviewUI: MoviePreviewUI,
        val dateRange: DateRange,
    ) : ScreenAd

    data class Advertisement(
        val id: Int = 0,
        val advertisement: Image.DrawableImage,
    ) : ScreenAd
}

fun Screen.toPreviewUI(image: Image<Any>) =
    ScreenAd.ScreenPreviewUi(
        id = id,
        moviePreviewUI = movie.toPreviewUI(image),
        dateRange = dateRange,
    )
