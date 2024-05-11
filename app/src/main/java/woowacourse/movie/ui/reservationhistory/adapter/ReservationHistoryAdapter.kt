package woowacourse.movie.ui.reservationhistory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.data.ReservationTicket
import woowacourse.movie.databinding.HolderReservationHistoryBinding

class ReservationHistoryAdapter(
    private val onItemClick: (id: Int) -> Unit,
) : ListAdapter<ReservationTicket, ReservationHistoryViewHolder>(
        object : DiffUtil.ItemCallback<ReservationTicket>() {
            override fun areItemsTheSame(
                oldItem: ReservationTicket,
                newItem: ReservationTicket,
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ReservationTicket,
                newItem: ReservationTicket,
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
