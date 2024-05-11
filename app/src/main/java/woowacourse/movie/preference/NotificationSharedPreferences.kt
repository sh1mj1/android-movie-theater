package woowacourse.movie.preference

import android.content.Context
import android.content.SharedPreferences

class NotificationSharedPreferences(private val notificationPreference: SharedPreferences) : NotificationPreference {
    override fun saveNotificationPreference(enabled: Boolean) {
        notificationPreference.edit().putBoolean(NOTIFICATION_ENABLED, enabled).apply()
    }

    override fun loadNotificationPreference(): Boolean = notificationPreference.getBoolean(NOTIFICATION_ENABLED, false)

    companion object {
        @Volatile
        private var instance: NotificationSharedPreferences? = null

        private const val APP_SETTINGS_PREFS = "app_settings_prefs"
        const val NOTIFICATION_ENABLED = "notification_enabled"

        fun getInstance(context: Context): NotificationSharedPreferences {
            return instance ?: synchronized(this) {
                instance ?: NotificationSharedPreferences(
                    context.getSharedPreferences(
                        APP_SETTINGS_PREFS,
                        Context.MODE_PRIVATE,
                    ),
                ).also { instance = it }
            }
        }
    }
}
