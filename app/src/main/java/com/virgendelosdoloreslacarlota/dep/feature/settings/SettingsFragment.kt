package com.virgendelosdoloreslacarlota.dep.feature.settings

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.browser.customtabs.CustomTabsClient.getPackageName
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.virgendelosdoloreslacarlota.dep.BuildConfig
import com.virgendelosdoloreslacarlota.dep.R
import com.virgendelosdoloreslacarlota.dep.Tracker
import com.virgendelosdoloreslacarlota.dep.analytics.ScreenEvent
import com.virgendelosdoloreslacarlota.dep.analytics.UserEvents
import com.virgendelosdoloreslacarlota.dep.base.BaseFragment
import com.virgendelosdoloreslacarlota.dep.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SettingsFragment : BaseFragment<SettingsInterfaces.State,
        SettingsInterfaces.Effect, SettingsViewModel, FragmentSettingsBinding>
    (FragmentSettingsBinding::inflate) {

    @Inject
    lateinit var tracker: Tracker

    override val viewModel: SettingsViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        setToolbarTitle(getString(R.string.settings))
        tracker.setScreen(ScreenEvent.Settings)
    }

    override fun viewCreated() {
        viewModel.setEvent(SettingsInterfaces.Event.LoadData)
        val versionNaming = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
        binding.version.text = versionNaming
    }

    override fun handleState(state: SettingsInterfaces.State) {
        when (state.currentState) {
            SettingsInterfaces.LoadingScreenState.Idle -> {
                binding.loading.isVisible = true
                binding.notifications.isEnabled = false
                binding.legalTexts.isEnabled = false
                binding.version.isVisible = false
            }
            SettingsInterfaces.LoadingScreenState.Success -> {
                binding.loading.isVisible = false
                binding.notifications.isEnabled = true
                binding.legalTexts.isEnabled = true
                binding.version.isVisible = true
                binding.legalTexts.setOnClickListener {
                    tracker.setEvent(UserEvents.LegalListTap)
                    findNavController().navigate(
                        SettingsFragmentDirections.actionSettingsFragmentToLegalsListFragment())
                }
                binding.notifications.setOnClickListener {
                    tracker.setEvent(UserEvents.NotificationsSettingsTap)
                    goToNotificationSettings()
                }
            }
        }
    }

    private fun goToNotificationSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val uri: Uri = Uri.fromParts("package", requireContext().packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    override fun handleEffect(effect: SettingsInterfaces.Effect) {

    }
}