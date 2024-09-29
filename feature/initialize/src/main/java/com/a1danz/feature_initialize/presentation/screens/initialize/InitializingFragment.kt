package com.a1danz.feature_initialize.presentation.screens.initialize

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.common.di.featureprovide.FeatureContainer
import com.a1danz.common.presentation.base.BaseActivity
import com.a1danz.common.presentation.base.BaseFragment
import com.a1danz.feature_initialize.R
import com.a1danz.feature_initialize.databinding.FragmentInitializingBinding
import com.a1danz.feature_initialize.di.InitializingComponent
import kotlinx.coroutines.launch

class InitializingFragment : BaseFragment(R.layout.fragment_initializing) {

    private val viewBinding: FragmentInitializingBinding by viewBinding(FragmentInitializingBinding::bind)

    private val viewModel: InitializingViewModel by viewModels { vmFactory }

    override fun inject() {
        (requireActivity().application as FeatureContainer)
            .getFeature(InitializingComponent::class.java)
            .inject(this)
    }

    override fun subscribe() {
        viewModel.userAuthorizedState.observe { isAuthorized ->
            if (isAuthorized) {
                viewModel.goToMainScreen()
                (requireActivity() as BaseActivity).activateBnv()
            } else {
                viewModel.goToAuthorizationScreen()
            }
        }
    }

    override fun initViews() {
        viewModel.checkUserAuthorization()
    }
}