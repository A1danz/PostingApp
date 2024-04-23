package com.a1danz.feature_authorization.presentation.screens.signup

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
import com.a1danz.common.di.featureprovide.FeatureContainer
import com.a1danz.common.presentation.base.BaseFragment
import com.a1danz.feature_authorization.AuthorizationRouter
import com.a1danz.feature_authorization.R
import com.a1danz.feature_authorization.databinding.FragmentSignupBinding
import com.a1danz.feature_authorization.di.AuthComponent
import com.a1danz.feature_authorization.utils.AuthorizationCodes
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpFragment : BaseFragment(R.layout.fragment_signup) {
    private val viewBinding: FragmentSignupBinding by viewBinding(FragmentSignupBinding::bind)
    private val viewModel : SignUpViewModel by viewModels { vmFactory }

    override fun inject() {
        (requireActivity().applicationContext as FeatureContainer).getFeature(
            AuthComponent::class.java
        ).inject(this)
    }

    override fun initViews() {
        with(viewBinding) {
            btnSignup.setOnClickListener {
                val name = inputName.text?.toString() ?: ""
                val email = inputEmail.text?.toString() ?: ""
                val password = inputPassword.text?.toString() ?: ""
                val repeatPassword = inputRepeatPassword.text?.toString() ?: ""
                if (email.isBlank() || password.isBlank() || name.isBlank()) {
                    showError(getString(R.string.empty_input))
                } else if (!email.matches(RegexValues.EMAIL_REGEX)) {
                    showError(getString(R.string.invalid_email))
                } else if (password != repeatPassword) {
                    showError(getString(R.string.password_dont_match))
                } else {
                    viewModel.doSignUp(email, password, name)
                }
            }

            btnMoveToRegister.setOnClickListener {
                viewModel.moveToSignIn()
            }
        }
    }

    override fun subscribe() {
        lifecycleScope.launch {
            viewModel.signUpResultFlow.collect { result ->
                if (result == AuthorizationCodes.SUCCESS_AUTH_CODE) {
                    viewModel.moveToAuthorizedState()
                } else if (result.isNotEmpty()) {
                    showError(result)
                }
            }
        }
    }

    private fun showError(msg: String) {
        with(viewBinding) {
            tvError.text = msg
            tvError.visibility = View.VISIBLE
        }
    }
}