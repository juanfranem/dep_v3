package com.virgendelosdoloreslacarlota.dep.feature.main

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.navigation.ui.NavigationUI
import com.virgendelosdoloreslacarlota.dep.R
import com.virgendelosdoloreslacarlota.dep.Tracker
import com.virgendelosdoloreslacarlota.dep.analytics.UserEvents
import com.virgendelosdoloreslacarlota.dep.databinding.ActivityMainBinding
import com.virgendelosdoloreslacarlota.dep.helper.showSnackBarErrorMessage
import com.virgendelosdoloreslacarlota.dep.helper.showSnackBarSuccessMessage
import com.virgendelosdoloreslacarlota.dep.helper.showSnackBarWarningMessage
import com.virgendelosdoloreslacarlota.dep.services.FirebaseTokenService
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var tracker: Tracker
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var firebaseTokenService: FirebaseTokenService

    private val navController: NavController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.HomeFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        askNotificationPermission()
        firebaseTokenService.sendCurrentTokenToServer()
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.toolbar.isVisible = destination.id != R.id.SplashFragment
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = NotificationManagerCompat.from(this)
            val channel = NotificationChannel(
                getString(R.string.default_notification_channel_id),
                "D.E.P. La Carlota",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    val writePermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (!isGranted) {
                binding.root.showSnackBarErrorMessage(getString(R.string.permission_required))
            }
        }

    fun setToolbarTitle(title: String, subTitle: String? = null) {
        binding.toolbar.title = title
        binding.toolbar.subtitle = subTitle
    }

    fun openUrl(url: String) {
        val backgroundColor = Color.WHITE
        val defaultColor = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(backgroundColor)
            .build()
        val builder = CustomTabsIntent.Builder()
        val customTabIntent = builder
            .setDefaultColorSchemeParams(defaultColor)
            .build()
        customTabIntent.launchUrl(this, Uri.parse(url))
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                tracker.setEvent(UserEvents.SettingsTap)
                navController.navigate(R.id.SettingsFragment, null, NavOptions.Builder()
                    .setEnterAnim(R.anim.slide_in)
                    .setExitAnim(R.anim.slide_out)
                    .setPopEnterAnim(R.anim.slide_in)
                    .setPopExitAnim(R.anim.slide_out)
                    .setPopUpTo(R.id.SettingsFragment, true)
                    .build()
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            createNotificationChannel()
            binding.root.showSnackBarSuccessMessage(getString(R.string.notifications_success))
        } else {
            binding.root.showSnackBarWarningMessage(getString(R.string.notifications_warning))
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                createNotificationChannel()
            }
        } else {
            createNotificationChannel()
        }
    }

    private fun handleIntent(intent: Intent) {
        navController.handleDeepLink(intent)
    }
}