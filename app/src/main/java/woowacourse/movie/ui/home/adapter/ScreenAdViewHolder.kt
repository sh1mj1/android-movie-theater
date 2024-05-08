package woowacourse.movie.ui.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.HolderAdvertisementBinding
import woowacourse.movie.databinding.HolderScreenBinding
import woowacourse.movie.ui.ScreenAd

sealed class ScreenAdViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(screenAd: ScreenAd)

    class ScreenViewHolder(
        private val binding: HolderScreenBinding,
        private val onReserveClick: (id: Int) -> Unit,
    ) : ScreenAdViewHolder(binding.root) {
        override fun bind(screenAd: ScreenAd) {
            if (screenAd is ScreenAd.ScreenPreviewUi) {
                binding.apply {
                    this.screenPreviewUi = screenAd
                    btnReserveNow.setOnClickListener { onReserveClick(screenAd.id) }
                }
            }
        }
    }

    class AdvertisementViewHolder(
        private val binding: HolderAdvertisementBinding,
        private val onItemClick: (id: Int) -> Unit,
    ) : ScreenAdViewHolder(binding.root) {
        override fun bind(screenAd: ScreenAd) {
            if (screenAd is ScreenAd.Advertisement) {
                binding.apply {
                    this.advertisement = screenAd
                    ivAdvertisement.setOnClickListener { onItemClick(screenAd.id) }
                }
            }
        }
    }
}
