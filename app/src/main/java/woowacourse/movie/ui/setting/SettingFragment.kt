package woowacourse.movie.ui.setting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.preference.NotificationPreference
import woowacourse.movie.preference.NotificationSharedPreferences

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private val notificationPreference: NotificationPreference by lazy {
        NotificationSharedPreferences.getInstance(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        showInitialPushNotification()
        initChangePushNotificationListener()
    }

    private fun initChangePushNotificationListener() {
        binding.switchPushAlarm.setOnCheckedChangeListener { _, isChecked ->
            notificationPreference.saveNotificationPreference(enabled = isChecked)
            Log.d(
                TAG,
                "notificationEnabled: ${notificationPreference.loadNotificationPreference()}",
            )
        }
    }

    private fun showInitialPushNotification() {
        val pushNotificationsEnabled = notificationPreference.loadNotificationPreference()
        binding.pushNotificationEnabled = pushNotificationsEnabled
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    companion object {
        private const val TAG = "SettingFragment"
    }
}
