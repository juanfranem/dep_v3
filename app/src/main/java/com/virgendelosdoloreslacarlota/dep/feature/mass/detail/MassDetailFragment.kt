package com.virgendelosdoloreslacarlota.dep.feature.mass.detail

import android.os.Build
import android.text.Html
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.virgendelosdoloreslacarlota.dep.R
import com.virgendelosdoloreslacarlota.dep.Tracker
import com.virgendelosdoloreslacarlota.dep.analytics.ScreenEvent
import com.virgendelosdoloreslacarlota.dep.base.BaseFragment
import com.virgendelosdoloreslacarlota.dep.databinding.FragmentMassDetailBinding
import com.virgendelosdoloreslacarlota.dep.helper.showSnackBarErrorMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MassDetailFragment : BaseFragment<MassDetailInterfaces.State,
        MassDetailInterfaces.Effect, MassDetailViewModel, FragmentMassDetailBinding>
    (FragmentMassDetailBinding::inflate) {
    override val viewModel: MassDetailViewModel by viewModels()
    private val args: MassDetailFragmentArgs by navArgs()
    @Inject
    lateinit var tracker: Tracker
    override fun viewCreated() {
        viewModel.setEvent(MassDetailInterfaces.Event.LoadData(args.slug))
        binding.birthDateTitle.setTranslation(R.string.birth_date_title)
        binding.churchTitle.setTranslation(R.string.church_title)
        binding.deadDateTitle.setTranslation(R.string.dead_date_title)
        binding.fromTitle.setTranslation(R.string.from_site_title)
        binding.nameTitle.setTranslation(R.string.name_title)
        binding.eventDateTitle.setTranslation(R.string.mass_date_title)
        binding.lastNameTitle.setTranslation(R.string.last_name_title)
        binding.subNameTitle.setTranslation(R.string.sub_name_title)
    }


    private fun TextView.setTranslation(@StringRes resource: Int) {
        text = viewModel.getTranslation(getString(resource))
    }

    override fun handleState(state: MassDetailInterfaces.State) {
        when (state.currentDataState) {
            MassDetailInterfaces.LoadingScreenState.Idle -> {
                binding.screen.isVisible = false
                binding.shareView.isVisible = false
                binding.loading.isVisible = true
            }
            MassDetailInterfaces.LoadingScreenState.Loading -> {
                binding.screen.isVisible = false
                binding.shareView.isVisible = false
                binding.loading.isVisible = true
            }
            is MassDetailInterfaces.LoadingScreenState.Success -> {
                binding.screen.isVisible = true
                binding.shareView.isVisible = true
                binding.loading.isVisible = false
                binding.image.load(state.currentDataState.mass.image)
                with(state.currentDataState.mass) {
                    binding.churchValue.text = church
                    binding.birthDateValue.text = deceased.birthDate
                    binding.deadDateValue.text = deceased.deathDate
                    binding.fromValue.text = deceased.from
                    binding.descriptionValue.text =
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Html.fromHtml(deceased.description, Html.FROM_HTML_MODE_LEGACY)
                        } else {
                            Html.fromHtml(deceased.description)
                        }
                    binding.lastNameValue.text = deceased.lastName
                    binding.subNameValue.text = deceased.subName
                    binding.nameValue.text = deceased.name
                    binding.eventDateValue.text = date

                    binding.descriptionRow.isVisible = !deceased.description.isNullOrBlank()
                    binding.fromRow.isVisible = !deceased.from.isNullOrBlank()
                    binding.birthDateRow.isVisible = !deceased.birthDate.isNullOrBlank()
                    binding.subNameRow.isVisible = !deceased.subName.isNullOrBlank()
                    if (deceased.description.isNullOrBlank()) {
                        binding.churchRow.background = null
                    }
                    binding.shareView.setOnSaveClickListener(requireActivity(), image)
                    binding.shareView.setOnShareClickListener(url, getString(R.string.share_mass))
                }
                setToolbarTitle(state.currentDataState.mass.deceased.fullName)
            }
            else -> {
                binding.loading.isVisible = false
                binding.root.showSnackBarErrorMessage(getString(R.string.error_message))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        tracker.setScreen(ScreenEvent.MassDetail(args.slug))
    }

    override fun handleEffect(effect: MassDetailInterfaces.Effect) {

    }
}