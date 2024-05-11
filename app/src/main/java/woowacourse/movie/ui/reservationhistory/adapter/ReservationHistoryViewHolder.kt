package woowacourse.movie.ui.reservationhistory.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.data.ReservationTicket
import woowacourse.movie.databinding.HolderReservationHistoryBinding

class ReservationHistoryViewHolder(
    private val binding: HolderReservationHistoryBinding,
    private val onItemClick: (id: Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(reservationTicket: ReservationTicket) {
        binding.reservationTicket = reservationTicket
        binding.root.setOnClickListener {
            onItemClick(reservationTicket.id)
        }
    }
}
