@file:JvmName("DateRangeFormatter")

package woowacourse.movie.ui

import woowacourse.movie.domain.model.DateRange

fun dateRangeFormat(dateRange: DateRange): String = "${dateRange.start} ~ ${dateRange.endInclusive}"
