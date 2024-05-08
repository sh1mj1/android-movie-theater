package woowacourse.movie.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import woowacourse.movie.ui.ScreenAd

class ScreenPreviewUiDiffUtil : DiffUtil.ItemCallback<ScreenAd>() {
    override fun areItemsTheSame(
        oldItem: ScreenAd,
        newItem: ScreenAd,
    ): Boolean =
        when (oldItem) {
            is ScreenAd.ScreenPreviewUi -> {
                oldItem.id == (newItem as ScreenAd.ScreenPreviewUi).id
            }

            is ScreenAd.Advertisement -> {
                oldItem.id == (newItem as ScreenAd.Advertisement).id
            }
        }

    override fun areContentsTheSame(
        oldItem: ScreenAd,
        newItem: ScreenAd,
    ): Boolean = oldItem == newItem
}
