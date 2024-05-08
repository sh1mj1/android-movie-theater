package woowacourse.movie.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import woowacourse.movie.R
import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.domain.model.ScreenTimePolicy
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.WeeklyScreenTimePolicy
import woowacourse.movie.domain.repository.DummyMovies
import woowacourse.movie.domain.repository.DummyReservation
import woowacourse.movie.domain.repository.DummyScreens
import woowacourse.movie.ui.ScreenDetailUi
import woowacourse.movie.ui.detail.view.DateTimeSpinnerView
import woowacourse.movie.ui.detail.view.OnItemSelectedListener
import woowacourse.movie.ui.detail.view.ScreenDetailInformationView
import woowacourse.movie.ui.detail.view.TicketReserveListener
import woowacourse.movie.ui.detail.view.TicketView
import woowacourse.movie.ui.seat.SeatReservationActivity

class ScreenDetailActivity : AppCompatActivity(), ScreenDetailContract.View {
    private lateinit var presenter: ScreenDetailContract.Presenter

    private val screenDetailView: ScreenDetailInformationView by lazy { findViewById(R.id.screen_detail_screen_view) }
    private val ticketView: TicketView by lazy { findViewById(R.id.screen_detail_ticket_view) }
    private val dateTimeSpinnerView: DateTimeSpinnerView by lazy { findViewById(R.id.screen_detail_date_time_spinner_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_detail)

        initPresenter()
        initView()
    }

    private fun initPresenter() {
        val screenId = intent.getIntExtra(PUT_EXTRA_KEY_ID, DEFAULT_SCREEN_ID)
        val theaterId = intent.getIntExtra(PUT_EXTRA_THEATER_ID_KEY, DEFAULT_THEATER_ID)
        presenter =
            ScreenDetailPresenter(
                this,
                DummyMovies(),
                DummyScreens(),
                DummyReservation,
                WeeklyScreenTimePolicy(),
                screenId,
                theaterId,
            )
    }

    private fun initView() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        with(presenter) {
            loadScreen()
            loadTicket()
        }

        initClickListener()
    }

    private fun initClickListener() {
        ticketView.initClickListener(
            object : TicketReserveListener {
                override fun increaseTicket() {
                    presenter.plusTicket()
                }

                override fun decreaseTicket() {
                    presenter.minusTicket()
                }

                override fun reserve() {
                    presenter.reserve()
                }
            },
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        with(outState) {
            putInt(PUT_TICKET_STATE_KEY, ticketView.ticketCount())
            putInt(PUT_DATE_POSITION_KEY, dateTimeSpinnerView.selectedDatePosition())
            putInt(PUT_TIME_POSITION_KEY, dateTimeSpinnerView.selectedTimePosition())
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState.let { bundle ->
            val count = bundle.getInt(PUT_TICKET_STATE_KEY)
            ticketView.updateTicketCount(count)
            presenter.saveTicket(count)

            val datePosition = bundle.getInt(PUT_DATE_POSITION_KEY)
            dateTimeSpinnerView.restoreDatePosition(datePosition)
            presenter.saveDatePosition(datePosition)

            val timePosition = bundle.getInt(PUT_TIME_POSITION_KEY)
            dateTimeSpinnerView.restoreTimePosition(timePosition)
            presenter.saveTimePosition(timePosition)
        }
    }

    override fun showScreen(screen: ScreenDetailUi) {
        screenDetailView.show(screen)
    }

    override fun showTicket(count: Int) {
        ticketView.updateTicketCount(count)
    }

    override fun showDateTimePicker(
        dateRange: DateRange,
        screenTimePolicy: ScreenTimePolicy,
        onDateSelectedListener: OnItemSelectedListener,
        onTimeSelectedListener: OnItemSelectedListener,
    ) {
        dateTimeSpinnerView.show(dateRange, screenTimePolicy, onDateSelectedListener, onTimeSelectedListener)
    }

    override fun showSeatsReservation(
        timeReservationId: Int,
        theaterId: Int,
    ) {
        SeatReservationActivity.startActivity(
            context = this,
            timeReservationId = timeReservationId,
            theaterId = theaterId,
        )
    }

    override fun showScreenFail(e: Throwable) {
        when (e) {
            is NoSuchElementException -> showToastMessage(e)
            else -> unexpectedFinish(e)
        }
    }

    override fun showTicketFail(e: Throwable) {
        showToastMessage(e)
    }

    private fun showToastMessage(e: Throwable) {
        when (e) {
            is NoSuchElementException -> Toast.makeText(this, "해당하는 상영 정보가 없습니다!!", Toast.LENGTH_SHORT).show()
            is IllegalArgumentException ->
                Toast.makeText(
                    this,
                    "티켓 수량은 ${Ticket.MIN_TICKET_COUNT}~${Ticket.MAX_TICKET_COUNT} 사이어야 합니다!!",
                    Toast.LENGTH_SHORT,
                ).show()

            else -> Toast.makeText(this, "예상치 못한 에러가 발생했습니다!!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun unexpectedFinish(e: Throwable) {
        showSnackBar(e)
        finish()
    }

    private fun showSnackBar(e: Throwable) {
        when (e) {
            is NoSuchElementException ->
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "해당하는 상영 정보가 없습니다!!",
                    Snackbar.LENGTH_SHORT,
                ).show()

            is IllegalArgumentException ->
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "티켓 수량은 ${Ticket.MIN_TICKET_COUNT}~${Ticket.MAX_TICKET_COUNT} 사이어야 합니다!!",
                    Toast.LENGTH_SHORT,
                ).show()

            else ->
                Snackbar.make(findViewById(android.R.id.content), "예상치 못한 에러가 발생했습니다!!", Snackbar.LENGTH_SHORT)
                    .show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    companion object {
        private const val DEFAULT_SCREEN_ID = -1
        private const val DEFAULT_THEATER_ID = -1
        private const val PUT_EXTRA_KEY_ID = "screenId"
        private const val PUT_EXTRA_THEATER_ID_KEY = "theaterId"
        private const val PUT_TICKET_STATE_KEY = "ticketCount"
        private const val PUT_DATE_POSITION_KEY = "datePosition"
        private const val PUT_TIME_POSITION_KEY = "timePosition"

        fun startActivity(
            context: Context,
            screenId: Int,
            theaterId: Int,
        ) {
            val intent = Intent(context, ScreenDetailActivity::class.java)
            intent.putExtra(PUT_EXTRA_KEY_ID, screenId)
            intent.putExtra(PUT_EXTRA_THEATER_ID_KEY, theaterId)
            context.startActivity(intent)
        }
    }
}
