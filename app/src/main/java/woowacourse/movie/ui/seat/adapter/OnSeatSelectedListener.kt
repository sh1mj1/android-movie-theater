package woowacourse.movie.ui.seat.adapter

import woowacourse.movie.domain.model.Seat

interface OnSeatSelectedListener {
    fun onSeatSelected(seat: Seat)

    fun onSeatDeselected(seat: Seat)
}
