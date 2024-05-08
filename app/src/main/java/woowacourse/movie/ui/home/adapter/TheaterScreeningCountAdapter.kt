package woowacourse.movie.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.databinding.HolderTheaterBinding
import woowacourse.movie.domain.model.TheaterScreeningCount

class TheaterScreeningCountAdapter(private val onTheaterClicked: (theaterId: Int) -> Unit) :
    ListAdapter<TheaterScreeningCount, TheaterScreeningCountViewHolder>(TheaterScreeningCountDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterScreeningCountViewHolder {
        val binding = HolderTheaterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TheaterScreeningCountViewHolder(binding, onTheaterClicked)
    }

    override fun onBindViewHolder(
        holder: TheaterScreeningCountViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }
}
