package woowacourse.movie.ui.setting

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.preference.AppPreference

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

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
            val sharedPreferences =
                requireContext().applicationContext.getSharedPreferences(
                    AppPreference.APP_SETTINGS_PREFS,
                    Context.MODE_PRIVATE,
                )

            sharedPreferences.edit().putBoolean(AppPreference.NOTIFICATION_ENABLED, isChecked).apply()
            Log.d(
                TAG,
                "isNotificationEnabled: ${sharedPreferences.getBoolean(AppPreference.NOTIFICATION_ENABLED, false)}",
            )
        }
    }

    private fun showInitialPushNotification() {
        val sharedPreferences =
            requireContext().applicationContext.getSharedPreferences(
                AppPreference.APP_SETTINGS_PREFS,
                Context.MODE_PRIVATE,
            )
        val pushNotificationsEnabled = sharedPreferences.getBoolean(AppPreference.NOTIFICATION_ENABLED, false)
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
