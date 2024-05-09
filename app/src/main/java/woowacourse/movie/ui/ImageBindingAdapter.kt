@file:JvmName("ImageBindingAdapter")

package woowacourse.movie.ui

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import woowacourse.movie.domain.model.Image

@BindingAdapter("image:drawableImage")
fun setImageResource(
    imageView: ImageView,
    @DrawableRes resId: Int,
) {
    imageView.setImageResource(resId)
}

@BindingAdapter("image:imageSource")
fun setImageResource(
    imageView: ImageView,
    image: Image<Any>,
) {
    when (image.imageSource) {
        is Int -> setImageResource(imageView, image.imageSource as Int)
        is String -> { /* Load image from URL or something */ }
    }
}
