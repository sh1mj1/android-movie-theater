package woowacourse.movie.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.domain.model.Seat

@BindingAdapter("seats:textFormat")
fun textUiFormat(
    textView: TextView,
    seats: List<Seat>,
) {
    textView.text =
        seats.joinToString(
            separator = ",",
            postfix = " |",
        ) { "${'A' + it.position.row}${it.position.col + 1}" }
}

@BindingAdapter("seat:seatName")
fun seatName(
    textView: TextView,
    seat: Seat,
) {
    textView.text = "${'A' + seat.position.row} ${seat.position.col + 1}"
}
