package woowacourse.movie.domain.model

import androidx.annotation.DrawableRes

sealed class Image<out T> {
    abstract val imageSource: T

    data class DrawableImage(
        @DrawableRes override val imageSource: Int,
    ) : Image<Int>()

    data class StringImage(override val imageSource: String) : Image<String>()
}
