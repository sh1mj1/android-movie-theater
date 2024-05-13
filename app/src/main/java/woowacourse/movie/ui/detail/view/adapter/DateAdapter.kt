package woowacourse.movie.ui.detail.view.adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.ui.detail.view.OnItemSelectedListener
import java.time.LocalDate

class DateAdapter(
    context: Context,
    dateRange: DateRange,
    private val onItemSelectedListener: OnItemSelectedListener,
) : ArrayAdapter<LocalDate>(context, android.R.layout.simple_spinner_item, dateRange.allDates()), AdapterView.OnItemSelectedListener {
    init {
        this.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }

    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long,
    ) {
        onItemSelectedListener.onItemSelected(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.e(TAG, "Nothing Selected")
    }

    companion object {
        const val TAG = "DateAdapter"
    }
}
