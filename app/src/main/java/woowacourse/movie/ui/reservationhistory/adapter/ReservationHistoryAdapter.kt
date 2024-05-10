package woowacourse.movie.ui.reservationhistory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.databinding.HolderReservationHistoryBinding
import woowacourse.movie.domain.model.Reservation

class ReservationHistoryAdapter(
    private val onItemClick: (id: Int) -> Unit,
) : ListAdapter<Reservation, ReservationHistoryViewHolder>(
        object : DiffUtil.ItemCallback<Reservation>() {
            override fun areItemsTheSame(
                oldItem: Reservation,
                newItem: Reservation,
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Reservation,
                newItem: Reservation,
            ): Boolean = oldItem == newItem
        },
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReservationHistoryViewHolder {
        val binding = HolderReservationHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationHistoryViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(
        holder: ReservationHistoryViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }
}
