package woowacourse.movie.ui.detail.view.adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import woowacourse.movie.domain.model.DateRange
import java.time.LocalDate

class DateAdapter(
    context: Context,
    dateRange: DateRange,
    val updateTime: (LocalDate) -> Unit,
    val selectDate: (Int) -> Unit,
) : ArrayAdapter<LocalDate>(context, android.R.layout.simple_spinner_item, dateRange.allDates()) {
    init {
        this.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    fun initClickListener(): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                val date = this@DateAdapter.getItem(position)
                date?.let { selectedDate ->
                    updateTime(selectedDate)
                }
                selectDate(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.e("ScreenDetailDateTimeSpinnerView", "Nothing Selected")
            }
        }
    }
}
