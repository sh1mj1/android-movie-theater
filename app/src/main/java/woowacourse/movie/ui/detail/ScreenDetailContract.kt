package woowacourse.movie.ui.detail

import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.domain.model.ScreenTimePolicy
import woowacourse.movie.ui.ScreenDetailUi
import woowacourse.movie.ui.detail.view.OnItemSelectedListener

interface ScreenDetailContract {
    interface View {
        fun showScreen(screen: ScreenDetailUi)

        fun showTicket(count: Int)

        fun showDateTimePicker(
            dateRange: DateRange,
            screenTimePolicy: ScreenTimePolicy,
            onDateSelectedListener: OnItemSelectedListener,
            onTimeSelectedListener: OnItemSelectedListener,
        )

        fun showDate(datePosition: Int)

        fun showTime(timePosition: Int)

        fun showSeatsReservation(
            timeReservationId: Int,
            theaterId: Int,
        )

        fun showScreenFail(e: Throwable)

        fun showTicketFail(e: Throwable)
    }

    interface Presenter {
        fun loadScreen()

        fun loadTicket()

        fun saveDatePosition(datePosition: Int)

        fun saveTimePosition(timePosition: Int)

        fun saveTicket(count: Int)

        fun plusTicket()

        fun minusTicket()

        fun reserve()
    }
}
