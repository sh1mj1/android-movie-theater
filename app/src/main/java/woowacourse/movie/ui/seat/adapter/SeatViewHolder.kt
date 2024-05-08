package woowacourse.movie.ui.seat.adapter

import android.util.Log
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
            if (seatView.isSelected) {
                Log.d(TAG, "bind: deselected")
                onSeatSelectedListener.onSeatDeselected(seat)

                // TODO: 선택된 seatView 들의 개수가 ticketCount 보다 작을 때만 선택 가능하도록 해야 함.
                seatView.isSelected = false
            } else {
                Log.d(TAG, "bind: selected")
                onSeatSelectedListener.onSeatSelected(seat)

                // TODO: 선택된 seatView 들의 개수가 ticketCount 보다 많을 때만 선택 가능하도록 해야 함.
                seatView.isSelected = true
            }
        }
    }

    companion object {
        private const val TAG = "SeatViewHolder"
    }
}
