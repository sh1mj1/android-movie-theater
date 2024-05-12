package woowacourse.movie.ui.setting

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import woowacourse.movie.MovieReservationApplication
import woowacourse.movie.R
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

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        showPushNotificationSetting()
        initChangePushNotificationListener()
    }

    private fun showPushNotificationSetting() {
        binding.pushNotificationEnabled = notificationPreference.loadNotificationPreference()
    }

    private fun initChangePushNotificationListener() {
        binding.switchPushAlarm.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                binding.pushNotificationEnabled = notificationPreference.loadNotificationPreference()
                showPermissionChangingGuideSnackBar()
            }
        }
    }

    private fun showPermissionChangingGuideSnackBar() {
        Snackbar.make(binding.root, R.string.push_notification_change_guide, Snackbar.LENGTH_LONG)
            .setAction(R.string.ok) {
                startApplicationDetailSettings()
            }.show()
    }

    private fun startApplicationDetailSettings() {
        val intent =
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                data = Uri.fromParts("package", requireContext().packageName, null)
            }
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        showPushNotificationSetting()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
