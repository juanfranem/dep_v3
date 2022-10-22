package com.virgendelosdoloreslacarlota.dep.feature.burial.detail

import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.virgendelosdoloreslacarlota.dep.R
import com.virgendelosdoloreslacarlota.dep.base.BaseFragment
import com.virgendelosdoloreslacarlota.dep.databinding.FragmentBurialDetailBinding
import com.virgendelosdoloreslacarlota.dep.helper.showSnackBarErrorMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BurialDetailFragment : BaseFragment<BurialDetailInterfaces.State,
        BurialDetailInterfaces.Effect, BurialDetailViewModel, FragmentBurialDetailBinding>
    (FragmentBurialDetailBinding::inflate) {
    override val viewModel: BurialDetailViewModel by viewModels()
    private val args: BurialDetailFragmentArgs by navArgs()


    override fun viewCreated() {
        viewModel.setEvent(BurialDetailInterfaces.Event.LoadData(args.slug))
        binding.birthDateTitle.setTranslation(R.string.birth_date_title)
        binding.churchTitle.setTranslation(R.string.church_title)
        binding.deadDateTitle.setTranslation(R.string.dead_date_title)
        binding.fromTitle.setTranslation(R.string.from_site_title)
        binding.nameTitle.setTranslation(R.string.name_title)
        binding.eventDateTitle.setTranslation(R.string.burial_date_title)
        binding.lastNameTitle.setTranslation(R.string.last_name_title)
        binding.subNameTitle.setTranslation(R.string.sub_name_title)
    }

    private fun TextView.setTranslation(@StringRes resource: Int) {
        text = viewModel.getTranslation(getString(resource))
    }

    override fun handleState(state: BurialDetailInterfaces.State) {
        when (state.currentDataState) {
            BurialDetailInterfaces.LoadingScreenState.Idle -> {
                binding.screen.isVisible = false
                binding.shareView.isVisible = false
                binding.loading.isVisible = true
            }
            BurialDetailInterfaces.LoadingScreenState.Loading -> {
                binding.screen.isVisible = false
                binding.shareView.isVisible = false
                binding.loading.isVisible = true
            }
            is BurialDetailInterfaces.LoadingScreenState.Success -> {
                binding.screen.isVisible = true
                binding.shareView.isVisible = true
                binding.loading.isVisible = false
                binding.image.load(state.currentDataState.burial.image)
                with(state.currentDataState.burial) {
                    binding.churchValue.text = church
                    binding.birthDateValue.text = deceased.birthDate
                    binding.deadDateValue.text = deceased.deathDate
                    binding.fromValue.text = deceased.from
                    binding.descriptionValue.text = deceased.description
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
                    binding.shareView.setOnShareClickListener(url, getString(R.string.share_burial))
                }
                setToolbarTitle(state.currentDataState.burial.deceased.fullName)
            }
            else -> {
                binding.loading.isVisible = false
                binding.root.showSnackBarErrorMessage(getString(R.string.error_message))
            }
        }
    }

    override fun handleEffect(effect: BurialDetailInterfaces.Effect) {

    }
}