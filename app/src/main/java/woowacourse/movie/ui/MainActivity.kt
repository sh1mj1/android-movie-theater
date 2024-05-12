package woowacourse.movie.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.snackbar.Snackbar
import woowacourse.movie.MovieReservationApplication
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.preference.NotificationPreference
import woowacourse.movie.ui.home.HomeFragment
import woowacourse.movie.ui.reservationhistory.ReservationHistoryFragment
import woowacourse.movie.ui.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_main) }

    private val reservationHistoryFragment: ReservationHistoryFragment by lazy { ReservationHistoryFragment() }
    private val homeFragment: HomeFragment by lazy { HomeFragment() }
    private val settingFragment: SettingFragment by lazy { SettingFragment() }

    private val notificationPreference: NotificationPreference by lazy {
        (application as MovieReservationApplication).notificationPreference
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            notificationPreference.saveNotificationPreference(isGranted)
            if (!isGranted) {
                showPermissionSnackBar()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            addFirstFragment(reservationHistoryFragment)
        }
        initBottomNavigationView()

        requestPermission()
    }

    private fun addFirstFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_container_view, fragment)
        }
    }

    private fun initBottomNavigationView() {
        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.reservation_history -> replaceFragment(reservationHistoryFragment)

                R.id.screen_home -> replaceFragment(homeFragment)

                R.id.setting -> replaceFragment(settingFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, fragment)
        }
    }

    private fun requestPermission() {
        when {
            Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU -> return
            hasPermission() -> notificationPreference.saveNotificationPreference(true)
            !hasPermission() -> showPermissionChangingGuide()
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun hasPermission() =
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun showPermissionChangingGuide() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
            showPermissionSnackBar()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun showPermissionSnackBar() {
        Snackbar.make(binding.root, R.string.push_notification_guide, Snackbar.LENGTH_LONG).setAction(R.string.ok) {
            startApplicationDetailSettings()
        }.show()
    }

    override fun onResume() {
        super.onResume()
        notificationPreference.saveNotificationPreference(checkedPermission())
    }

    private fun checkedPermission(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true
        return hasPermission()
    }

    private fun startApplicationDetailSettings() {
        val intent =
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                data = Uri.fromParts("package", packageName, null)
            }
        startActivity(intent)
    }
}
