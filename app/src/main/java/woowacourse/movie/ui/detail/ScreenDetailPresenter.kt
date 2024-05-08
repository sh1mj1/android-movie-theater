package woowacourse.movie.ui.detail

import woowacourse.movie.domain.model.DateTime
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.ScreenTimePolicy
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.Ticket.Companion.MIN_TICKET_COUNT
import woowacourse.movie.domain.model.WeeklyScreenTimePolicy
import woowacourse.movie.domain.repository.MovieRepository
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.ScreenRepository
import woowacourse.movie.ui.toDetailUI

class ScreenDetailPresenter(
    private val view: ScreenDetailContract.View,
    private val movieRepository: MovieRepository,
    private val screenRepository: ScreenRepository,
    private val reservationRepository: ReservationRepository,
    private val screenTimePolicy: ScreenTimePolicy = WeeklyScreenTimePolicy(),
    screenId: Int,
    private val theaterId: Int,
) : ScreenDetailContract.Presenter {
    private val loadedScreen: Screen = loadedScreen(screenId)
    private val dateRange = loadedScreen.dateRange
    private var ticket: Ticket = Ticket(MIN_TICKET_COUNT)

    private var datePosition: Int = 0

    private var timePosition: Int = 0

    override fun loadScreen() {
        try {
            view.showScreen(loadedScreen.toDetailUI(movieRepository.imageSrc(loadedScreen.movie.id)))
            view.showDateTimePicker(dateRange, screenTimePolicy, ::saveDatePosition, ::saveTimePosition)
        } catch (e: Exception) {
            when (e) {
                is NoSuchElementException -> view.goToBack(e)
                else -> view.unexpectedFinish(e)
            }
        }
    }

    override fun loadTicket() {
        view.showTicket(ticket.count)
    }

    override fun saveDatePosition(datePosition: Int) {
        this.datePosition = datePosition
    }

    override fun saveTimePosition(timePosition: Int) {
        this.timePosition = timePosition
    }

    override fun saveTicket(count: Int) {
        ticket = Ticket(count)
    }

    private fun loadedScreen(id: Int): Screen {
        screenRepository.findById(id = id).onSuccess { screen ->
            return screen
        }.onFailure { e ->
            throw e
        }
        throw IllegalStateException("예기치 못한 오류")
    }

    override fun plusTicket() {
        try {
            ticket = ticket.increase()
            view.showTicket(ticket.count)
        } catch (e: IllegalArgumentException) {
            view.showToastMessage(e)
        }
    }

    override fun minusTicket() {
        try {
            ticket = ticket.decrease()
            view.showTicket(ticket.count)
        } catch (e: IllegalArgumentException) {
            view.showToastMessage(e)
        }
    }

    override fun reserve() {
        val date = dateRange.allDates()[datePosition]

        reservationRepository.saveTimeReservation(
            loadedScreen,
            count = ticket.count,
            dateTime =
                DateTime(
                    date = date,
                    time = screenTimePolicy.screeningTimes(date)[timePosition],
                ),
        ).onSuccess { timeReservationId ->
            view.navigateToSeatsReservation(timeReservationId, theaterId)
        }.onFailure { e ->
            view.showToastMessage(e)
        }
    }

    companion object {
        private const val TAG = "ScreenDetailPresenter"
    }
}
