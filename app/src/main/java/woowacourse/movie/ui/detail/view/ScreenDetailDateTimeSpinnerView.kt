package woowacourse.movie.ui.detail.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.HolderSpinnerDateTimeBinding
import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.domain.model.ScreenTimePolicy
import woowacourse.movie.ui.detail.view.adapter.DateAdapter
import woowacourse.movie.ui.detail.view.adapter.TimeAdapter

class ScreenDetailDateTimeSpinnerView(context: Context, attrs: AttributeSet? = null) : DateTimeSpinnerView,
    ConstraintLayout(context, attrs) {
    private val binding: HolderSpinnerDateTimeBinding by lazy {
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.holder_spinner_date_time, this, true)
    }

    override fun show(
        dateRange: DateRange,
        screenTimePolicy: ScreenTimePolicy,
        onDateSelectedListener: OnItemSelectedListener,
        onTimeSelectedListener: OnItemSelectedListener,
    ) {
        val timeAdapter = initTimeAdapter(screenTimePolicy, dateRange, onTimeSelectedListener)
        val dateAdapter = initDateAdapter(dateRange, screenTimePolicy, timeAdapter, onDateSelectedListener)

        bindAdapters(timeAdapter, dateAdapter)
    }

    private fun initTimeAdapter(
        screenTimePolicy: ScreenTimePolicy,
        dateRange: DateRange,
        onTimeSelectedListener: OnItemSelectedListener,
    ): TimeAdapter {
        return TimeAdapter(context, screenTimePolicy, dateRange.start, onTimeSelectedListener = { position ->
            onTimeSelectedListener.onItemSelected(position)
        })
    }

    private fun initDateAdapter(
        dateRange: DateRange,
        screenTimePolicy: ScreenTimePolicy,
        timeAdapter: TimeAdapter,
        onDateSelectedListener: OnItemSelectedListener,
    ): DateAdapter =
        DateAdapter(context, dateRange) { position ->
            val times = screenTimePolicy.screeningTimes(dateRange.allDates()[position]).toList()

            timeAdapter.clear()
            timeAdapter.addAll(times)
            onDateSelectedListener.onItemSelected(position)
        }

    private fun bindAdapters(
        timeAdapter: TimeAdapter,
        dateAdapter: DateAdapter,
    ) {
        binding.timeAdapter = timeAdapter
        binding.spnTime.onItemSelectedListener = timeAdapter

        binding.dateAdapter = dateAdapter
        binding.spnDate.onItemSelectedListener = dateAdapter
    }

    override fun showDate(position: Int) {
        binding.spnDate.post { binding.spnDate.setSelection(position) }
    }

    override fun showTime(position: Int) {
        binding.spnDate.post { binding.spnTime.setSelection(position) }
    }

    override fun selectedDatePosition(): Int {
        return binding.spnDate.selectedItemPosition
    }

    override fun selectedTimePosition(): Int {
        return binding.spnTime.selectedItemPosition
    }
}
