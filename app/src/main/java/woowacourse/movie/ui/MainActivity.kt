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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
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

    private fun requestPermission() {
        when {
            Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU -> return
            hasPermission() -> {
                Log.d(TAG, "requestPermission: 이미 권한이 있어서 다시 요청하지 않음")
                notificationPreference.saveNotificationPreference(true)
            }

            !hasPermission() -> {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    Log.d(TAG, "requestPermission: 이전에 요청을 거부했어서 안내 메시지를 보여줘야 해.")
                    showPermissionSnackBar()
                } else {
                    Log.d(TAG, "requestPermission: 처음 권한 요청이라 바로 dialog")
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        notificationPreference.saveNotificationPreference(checkedPermission())
        Log.d(TAG, "onResume: notificationPreference: ${notificationPreference.loadNotificationPreference()}")
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun hasPermission() =
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS,
        ) == PackageManager.PERMISSION_GRANTED

    private fun checkedPermission(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true
        return hasPermission()
    }

    private fun showPermissionSnackBar() {
        Log.d(TAG, "showPermissionSnackBar: called")
        Snackbar.make(
            binding.root,
            "알림 권한이 필요합니다",
            Snackbar.LENGTH_LONG,
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

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            Log.d(TAG, "requestPermissionLauncher: granted: $isGranted")
            notificationPreference.saveNotificationPreference(isGranted)

            if (isGranted) {
                Log.d(TAG, "requestPermissionLauncher: 권한 허용")
            } else {
                Log.d(TAG, "requestPermissionLauncher: 권한 거부")
                showPermissionSnackBar()
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
