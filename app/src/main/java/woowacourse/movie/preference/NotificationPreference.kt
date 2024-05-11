package woowacourse.movie.preference

interface NotificationPreference {
    fun saveNotificationPreference(enabled: Boolean)

    fun loadNotificationPreference(): Boolean
}
