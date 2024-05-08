package woowacourse.movie.ui.seat.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.HolderSeatBinding
import woowacourse.movie.domain.model.Seat

class SeatViewHolder(
    private val binding: HolderSeatBinding,
    private val onSeatSelectedListener: OnSeatSelectedListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(seat: Seat) {
        binding.seat = seat
        binding.root.setOnClickListener { seatView ->
            when {
                seatView.isSelected -> onSeatSelectedListener.onSeatDeselected(seat, seatView)
                !seatView.isSelected -> onSeatSelectedListener.onSeatSelected(seat, seatView)
            }
        }
    }
}
