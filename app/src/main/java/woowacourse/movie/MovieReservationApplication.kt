package woowacourse.movie

import android.app.Application
import woowacourse.movie.preference.NotificationSharedPreferences

class MovieReservationApplication : Application() {
    val notificationPreference by lazy { NotificationSharedPreferences.getInstance(applicationContext) }
}
