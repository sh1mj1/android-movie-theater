package woowacourse.movie.ui.seat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.databinding.HolderSeatBinding
import woowacourse.movie.domain.model.Seat

class SeatsAdapter(
    private val onSeatSelectedListener: OnSeatSelectedListener,
) : ListAdapter<Seat, SeatViewHolder>(SeatDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SeatViewHolder {
        val binding = HolderSeatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeatViewHolder(binding, onSeatSelectedListener = onSeatSelectedListener)
    }

    override fun onBindViewHolder(
        holder: SeatViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }
}
