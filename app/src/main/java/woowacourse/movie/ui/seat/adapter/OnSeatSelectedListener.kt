package woowacourse.movie.ui.seat.adapter

import android.view.View
import woowacourse.movie.domain.model.Seat

interface OnSeatSelectedListener {
    fun onSeatSelected(
        seat: Seat,
        seatView: View,
    )

    fun onSeatDeselected(
        seat: Seat,
        seatView: View,
    )
}
