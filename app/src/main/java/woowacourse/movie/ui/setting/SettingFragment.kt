package woowacourse.movie.ui.setting

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import woowacourse.movie.MovieReservationApplication
import woowacourse.movie.databinding.FragmentSettingBinding
import woowacourse.movie.preference.NotificationPreference

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private val notificationPreference: NotificationPreference by lazy {
        (requireActivity().applicationContext as MovieReservationApplication).notificationPreference
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: notification enabled ${notificationPreference.loadNotificationPreference()}")
        showInitialPushNotification()
        initChangePushNotificationListener()
    }

    private fun showInitialPushNotification() {
        val pushNotificationsEnabled = notificationPreference.loadNotificationPreference()
        Log.d(TAG, "showInitialPushNotification: pushNoti is $pushNotificationsEnabled")
        binding.pushNotificationEnabled = pushNotificationsEnabled
    }

    private fun initChangePushNotificationListener() {
        binding.switchPushAlarm.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                binding.pushNotificationEnabled = notificationPreference.loadNotificationPreference()
                showPermissionChangingGuideSnackBar()
            } else {
                Log.d(TAG, "initChangePushNotificationListener: is less than TIRAMISU")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showInitialPushNotification()
        Log.d(TAG, "onResume: notification enabled ${notificationPreference.loadNotificationPreference()}")
    }

    private fun showPermissionChangingGuideSnackBar() {
        Log.d(TAG, "showPermissionSnackBar: called")
        Snackbar.make(
            binding.root,
            "푸시 알림 권한 설정 창에서 변경해주세요.",
            Snackbar.LENGTH_LONG,
        ).setAction("확인") {
            createdSettingsIntent()
        }.show()
    }

    private fun createdSettingsIntent() {
        val intent =
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                data = Uri.fromParts("package", requireContext().packageName, null)
            }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "SettingFragment"
    }
}
