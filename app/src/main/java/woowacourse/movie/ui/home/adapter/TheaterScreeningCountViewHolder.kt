package woowacourse.movie.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.HolderTheaterBinding
import woowacourse.movie.domain.model.TheaterScreeningCount

class TheaterScreeningCountViewHolder(
    private val binding: HolderTheaterBinding,
    private val itemClick: (theaterId: Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(theaterScreeningCount: TheaterScreeningCount) {
        binding.theater = theaterScreeningCount.theater
        binding.screeningCount = theaterScreeningCount.screeningCount
        binding.next.setOnClickListener {
            itemClick(theaterScreeningCount.theater.id)
        }
    }
}
