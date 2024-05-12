package woowacourse.movie.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.snackbar.Snackbar
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMainBinding
import woowacourse.movie.preference.NotificationPreference
import woowacourse.movie.preference.NotificationSharedPreferences
import woowacourse.movie.ui.home.HomeFragment
import woowacourse.movie.ui.reservationhistory.ReservationHistoryFragment
import woowacourse.movie.ui.setting.SettingFragment

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_main) }

    private val reservationHistoryFragment: ReservationHistoryFragment by lazy { ReservationHistoryFragment() }
    private val homeFragment: HomeFragment by lazy { HomeFragment() }
    private val settingFragment: SettingFragment by lazy { SettingFragment() }

    private val notificationPreference: NotificationPreference by lazy {
        NotificationSharedPreferences.getInstance(applicationContext)
    }

    /*
        private val notificationPreference by lazy { (application as MovieReservationApp).notificationDatastore }

     */

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Log.d(TAG, "requestPermissionLauncher: granted")
            } else {
                Log.e(TAG, "requestPermissionLauncher: denied")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        if (savedInstanceState == null) {
            addFirstFragment(reservationHistoryFragment)
        }

        initBottomNavigationView()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: notificationPreference: ${notificationPreference.loadNotificationPreference()}")
        requestPermission()
    }

    private fun requestPermission() {
        when {
            Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU -> return
            needPostNotificationPermission() -> {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    showPermissionSnackBar()
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }

            else -> notificationPreference.saveNotificationPreference(true)
        }
    }

    private fun needPostNotificationPermission() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        } else {
            false
        }

    private fun showPermissionSnackBar() {
        Snackbar.make(
            binding.root,
            "알림 권한이 필요합니다",
            Snackbar.LENGTH_INDEFINITE,
        ).setAction("확인") {
            createdSettingsIntent()
        }.show()
    }

    private fun createdSettingsIntent() {
        val intent =
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                data = Uri.fromParts("package", packageName, null)
            }
        startActivity(intent)
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

    companion object {
        private const val TAG = "MainActivity"
    }
}
