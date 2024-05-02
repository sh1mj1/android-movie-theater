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
import woowacourse.movie.domain.model.WeeklyScreenTimePolicy
import woowacourse.movie.ui.detail.view.adapter.DateAdapter
import woowacourse.movie.ui.detail.view.adapter.TimeAdapter
import java.time.LocalDate

class ScreenDetailDateTimeSpinnerView(context: Context, attrs: AttributeSet? = null) : DateTimeSpinnerView,
    ConstraintLayout(context, attrs) {
    private val binding: HolderSpinnerDateTimeBinding by lazy {
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.holder_spinner_date_time, this, true)
    }

    private lateinit var dateAdapter: DateAdapter
    private lateinit var timeAdapter: TimeAdapter

    override fun show(
        dateRange: DateRange,
        screenTimePolicy: WeeklyScreenTimePolicy,
        selectDateListener: SelectDateListener,
        selectTimeListener: SelectTimeListener,
    ) {
        initDateAdapter(
            dateRange,
            screenTimePolicy,
            selectDateListener,
        )
        initTimeAdapter(dateRange.start, screenTimePolicy, selectTimeListener)

        binding.spnDate.onItemSelectedListener = dateAdapter.initClickListener()
        binding.spnTime.onItemSelectedListener = timeAdapter.initClickListener()
    }

    override fun restoreDatePosition(position: Int) {
        binding.spnDate.setSelection(position)
    }

    override fun restoreTimePosition(position: Int) {
        binding.spnTime.setSelection(position)
    }

    override fun selectedDatePosition(): Int {
        return binding.spnDate.selectedItemPosition
    }

    override fun selectedTimePosition(): Int {
        return binding.spnTime.selectedItemPosition
    }

    private fun initDateAdapter(
        dateRange: DateRange,
        screenTimePolicy: ScreenTimePolicy,
        selectDateListener: SelectDateListener,
    ) {
        dateAdapter =
            DateAdapter(context, dateRange, {
                timeAdapter.clear()
                timeAdapter.addAll(screenTimePolicy.screeningTimes(it).toList())
            }, {
                selectDateListener.selectDate(it)
            })
        binding.dateAdapter = dateAdapter
    }

    private fun initTimeAdapter(
        date: LocalDate,
        screenTimePolicy: WeeklyScreenTimePolicy,
        selectTimeListener: SelectTimeListener,
    ) {
        timeAdapter =
            TimeAdapter(context, screenTimePolicy, date) {
                selectTimeListener.selectTime(it)
            }
        binding.timeAdapter = timeAdapter
    }
}
