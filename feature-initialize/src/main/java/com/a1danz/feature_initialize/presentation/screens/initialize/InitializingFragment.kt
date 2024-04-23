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
import com.a1danz.feature_initialize.presentation.navigation.InitializingRouter
import com.a1danz.feature_user_configurer.di.UserConfigurerComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InitializingFragment : BaseFragment(R.layout.fragment_initializing) {
    private val viewBinding: FragmentInitializingBinding by viewBinding(FragmentInitializingBinding::bind)
    private val viewModel: InitializingViewModel by viewModels { vmFactory }

    override fun initViews() {
        Log.d("INITIALIZE", "INITIALIZE SCREEN OPENED")
        lifecycleScope.launch {
            val checkFlow = viewModel.checkUser()
            checkFlow.collect {result ->
                if (result) {
                    (requireActivity() as BaseActivity).activateBnv()
                    Log.d("INITIALIZE", "ACTIVATE BNV")
                } else {
                    Log.d("INITIALIZE", "MOVE TO AUTH")
                }
            }
        }
    }

    override fun inject() {
        (requireActivity().application as FeatureContainer)
            .getFeature(InitializingComponent::class.java)
            .inject(this)
    }

    override fun subscribe() {
        return
    }

}