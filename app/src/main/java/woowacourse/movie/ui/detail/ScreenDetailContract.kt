package woowacourse.movie.ui.detail

import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.domain.model.WeeklyScreenTimePolicy
import woowacourse.movie.ui.ScreenDetailUI
import woowacourse.movie.ui.detail.view.SelectDateListener
import woowacourse.movie.ui.detail.view.SelectTimeListener

interface ScreenDetailContract {
    interface View {
        fun showScreen(screen: ScreenDetailUI)

        fun showTicket(count: Int)

        fun showDateTimePicker(
            dateRange: DateRange,
            screenTimePolicy: WeeklyScreenTimePolicy,
            selectDateListener: SelectDateListener,
            selectTimeListener: SelectTimeListener,
        )

        fun navigateToSeatsReservation(
            timeReservationId: Int,
            theaterId: Int,
        )

        fun showToastMessage(e: Throwable)

        fun showSnackBar(e: Throwable)

        fun goToBack(e: Throwable)

        fun unexpectedFinish(e: Throwable)
    }

    interface Presenter {
        fun loadScreen(screenId: Int)

        fun loadTicket()

        fun saveDatePosition(datePosition: Int)

        fun saveTimePosition(timePosition: Int)

        fun saveTicket(count: Int)

        fun plusTicket()

        fun minusTicket()

        fun reserve(
            screenId: Int,
            theaterId: Int,
        )
    }
}
