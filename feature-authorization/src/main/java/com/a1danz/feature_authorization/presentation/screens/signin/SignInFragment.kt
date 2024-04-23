package com.a1danz.feature_authorization.presentation.screens.signin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.common.core.config.RegexValues
import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.common.di.featureprovide.FeatureContainer
import com.a1danz.common.presentation.base.BaseFragment
import com.a1danz.feature_authorization.AuthorizationRouter
import com.a1danz.feature_authorization.R
import com.a1danz.feature_authorization.databinding.FragmentSigninBinding
import com.a1danz.feature_authorization.di.AuthComponent
import com.a1danz.feature_authorization.utils.AuthorizationCodes
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInFragment : BaseFragment(R.layout.fragment_signin) {
    private val viewBinding: FragmentSigninBinding by viewBinding(FragmentSigninBinding::bind)
    private val viewModel : SignInViewModel by viewModels { vmFactory }

    override fun inject() {
        (requireActivity().applicationContext as FeatureContainer).getFeature(
            AuthComponent::class.java
        ).inject(this)
    }

    override fun subscribe() {
        lifecycleScope.launch {
            viewModel.signInResultFlow.collect {
                if (it == AuthorizationCodes.SUCCESS_AUTH_CODE) {
                    viewModel.moveToAuthorizedState()
                } else if (it.isNotEmpty()) {
                    showError(it)
                }
            }
        }
    }

    override fun initViews() {
        with(viewBinding) {
            btnSignin.setOnClickListener {
                val email = inputEmail.text?.toString() ?: ""
                val password = inputPassword.text?.toString() ?: ""
                if (email.isBlank() || password.isBlank()) {
                    showError(getString(R.string.empty_input))
                } else if (!email.matches(RegexValues.EMAIL_REGEX)) {
                    showError(getString(R.string.invalid_email))
                } else {
                    viewModel.doSignIn(email, password)
                }
            }

            btnMoveToRegister.setOnClickListener {
                viewModel.moveToSignUp()
            }
        }
    }

    private fun showError(msg : String) {
        with(viewBinding) {
            tvError.text = msg
            tvError.visibility = View.VISIBLE
        }
    }
}