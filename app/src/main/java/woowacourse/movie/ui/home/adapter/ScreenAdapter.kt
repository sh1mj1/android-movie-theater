package woowacourse.movie.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.databinding.HolderAdvertisementBinding
import woowacourse.movie.databinding.HolderScreenBinding
import woowacourse.movie.domain.model.ScreenType
import woowacourse.movie.ui.ScreenAd

class ScreenAdapter(
    private val onScreenClick: (id: Int) -> Unit,
    private val onAdClick: (id: Int) -> Unit,
) : ListAdapter<ScreenAd, ScreenAdViewHolder>(ScreenPreviewUiDiffUtil()) {
    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is ScreenAd.ScreenPreviewUi -> ScreenType.SCREEN.id
            is ScreenAd.Advertisement -> ScreenType.ADVERTISEMENT.id
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ScreenAdViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            ScreenType.SCREEN.id -> {
                val binding = HolderScreenBinding.inflate(inflater, parent, false)
                ScreenAdViewHolder.ScreenViewHolder(binding, onScreenClick)
            }

            ScreenType.ADVERTISEMENT.id -> {
                val binding = HolderAdvertisementBinding.inflate(inflater, parent, false)
                ScreenAdViewHolder.AdvertisementViewHolder(binding, onAdClick)
            }

            else -> throw IllegalArgumentException("Invalid viewType: $viewType")
        }
    }

    override fun onBindViewHolder(
        holder: ScreenAdViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }
}
