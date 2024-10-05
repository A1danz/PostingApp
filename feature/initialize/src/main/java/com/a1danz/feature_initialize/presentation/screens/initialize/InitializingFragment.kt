package com.a1danz.feature_initialize.presentation.screens.initialize

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.common.di.featureprovide.FeatureContainer
import com.a1danz.common.presentation.base.BaseActivity
import com.a1danz.common.presentation.base.BaseFragment
import com.a1danz.feature_initialize.R
import com.a1danz.feature_initialize.databinding.FragmentInitializingBinding
import com.a1danz.feature_initialize.di.InitializingComponent
import com.a1danz.feature_initialize.presentation.model.event.InitializingEvent

class InitializingFragment : BaseFragment(R.layout.fragment_initializing) {

    private val viewBinding: FragmentInitializingBinding by viewBinding(FragmentInitializingBinding::bind)

    private val viewModel: InitializingViewModel by viewModels { vmFactory }

    override fun inject() {
        (requireActivity().application as FeatureContainer)
            .getFeature(InitializingComponent::class.java)
            .inject(this)
    }

    override fun subscribe() {
        viewModel.initializingEvent.observe {
            when(it) {
                InitializingEvent.NavigateToAuthorization -> {
                    viewModel.navigateToAuthorizationScreen()
                }
                InitializingEvent.NavigateToMain -> {
                    viewModel.navigateToMainScreen()
                    (requireActivity() as? BaseActivity)?.activateBnv()
                }
            }
        }

        viewModel.userIsAuthorizedState.observe {
            it?.let {
                viewModel.initializeUser()
            }
        }
    }

    override fun initViews() {
        viewModel.checkUserAuthorization()
    }
}