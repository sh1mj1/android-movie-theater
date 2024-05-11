package woowacourse.movie

import android.app.Application
import android.content.SharedPreferences

class MovieReservationApplication : Application() {
    val notificationPreference: SharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
}
