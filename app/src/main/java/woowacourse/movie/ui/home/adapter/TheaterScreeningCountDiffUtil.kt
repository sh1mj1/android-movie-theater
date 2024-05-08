package woowacourse.movie.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import woowacourse.movie.domain.model.TheaterScreeningCount

class TheaterScreeningCountDiffUtil : DiffUtil.ItemCallback<TheaterScreeningCount>() {
    override fun areItemsTheSame(
        oldItem: TheaterScreeningCount,
        newItem: TheaterScreeningCount,
    ): Boolean = oldItem.theater.id == newItem.theater.id

    override fun areContentsTheSame(
        oldItem: TheaterScreeningCount,
        newItem: TheaterScreeningCount,
    ): Boolean = oldItem == newItem
}
