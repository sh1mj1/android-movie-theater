package woowacourse.movie.ui.seat.adapter

import androidx.recyclerview.widget.DiffUtil
import woowacourse.movie.domain.model.Seat

class SeatDiffUtil : DiffUtil.ItemCallback<Seat>() {
    override fun areItemsTheSame(
        oldItem: Seat,
        newItem: Seat,
    ): Boolean = oldItem.position == newItem.position

    override fun areContentsTheSame(
        oldItem: Seat,
        newItem: Seat,
    ): Boolean = oldItem == newItem
}
