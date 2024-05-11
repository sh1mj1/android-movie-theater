package woowacourse.movie.ui.reservationhistory

import android.os.Handler
import android.os.Looper
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.repository.FakeReservationRepository

class ReservationHistoryPresenterTest {
    private lateinit var mockView: ReservationHistoryContract.View
    private lateinit var presenter: ReservationHistoryContract.Presenter

    @BeforeEach
    fun setUp() {
        mockkStatic(Looper::class)
        every { Looper.getMainLooper() } returns mockk(relaxed = true)

        mockkConstructor(Handler::class)
        every { anyConstructed<Handler>().post(any()) } answers {
            firstArg<Runnable>().run()
            true
        }

        mockView = mockk<ReservationHistoryContract.View>(relaxed = true)
        presenter = ReservationHistoryPresenter(mockView, FakeReservationRepository())
    }

    @Test
    fun `모든 영화 목록을 불러온다`() {
        // when
        presenter.loadAllReservationHistory()

        // then
        verify { mockView.showAllReservationHistory(emptyList()) }
    }
}
