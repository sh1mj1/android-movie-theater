package woowacourse.movie.ui.reservationhistory.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.HolderReservationHistoryBinding
import woowacourse.movie.domain.model.Reservation

class ReservationHistoryViewHolder(
    private val binding: HolderReservationHistoryBinding,
    private val onItemClick: (id: Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(reservation: Reservation) {
        binding.reservation = reservation
        binding.root.setOnClickListener {
            onItemClick(reservation.id)
        }
    }
}
