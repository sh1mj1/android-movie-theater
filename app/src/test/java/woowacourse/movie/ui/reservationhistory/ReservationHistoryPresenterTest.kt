package woowacourse.movie.ui.reservationhistory

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.repository.FakeReservationRepository

class ReservationHistoryPresenterTest {
    private lateinit var mockView: ReservationHistoryContract.View
    private lateinit var presenter: ReservationHistoryContract.Presenter

    @BeforeEach
    fun setUp() {
        mockView = mockk<ReservationHistoryContract.View>(relaxed = true)
        presenter = ReservationHistoryPresenter(mockView, FakeReservationRepository())
    }

    @Test
    fun `모든 영화 목록을 불러온다`() {
        // when
        presenter.loadAllReservationHistory()

        // then
        verify { mockView.showAllReservationHistory(listOf(Reservation.NULL)) }
    }
}
