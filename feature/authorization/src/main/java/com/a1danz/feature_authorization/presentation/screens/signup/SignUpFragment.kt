package com.a1danz.feature_authorization.presentation.screens.signup

import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.a1danz.common.core.config.RegexValues
import com.a1danz.common.di.featureprovide.FeatureContainer
import com.a1danz.common.ext.getString
import com.a1danz.common.presentation.base.BaseFragment
import com.a1danz.feature_authorization.R
import com.a1danz.feature_authorization.databinding.FragmentSignupBinding
import com.a1danz.feature_authorization.di.AuthComponent
import com.a1danz.feature_authorization.presentation.model.event.SignUpEvent

class SignUpFragment : BaseFragment<SignUpViewModel>(R.layout.fragment_signup) {

    private val viewBinding: FragmentSignupBinding by viewBinding(FragmentSignupBinding::bind)

    override val viewModel : SignUpViewModel by viewModels { vmFactory }

    override fun inject() {
        (requireActivity().applicationContext as FeatureContainer).getFeature(
            AuthComponent::class.java
        ).inject(this)
    }

    override fun subscribe() {
        viewModel.signUpEvent.observe {
            when(it) {
                SignUpEvent.NavigateToAuthorizedState -> {
                    viewModel.moveToAuthorizedState()
                }
                SignUpEvent.NavigateToSignIn -> {
                    viewModel.moveToSignIn()
                }
                is SignUpEvent.ShowError -> {
                    showError(it.readableError.uiMessage.getString(requireContext()))
                }
            }
        }
    }

    override fun initViews() {
        with(viewBinding) {
            btnSignup.setOnClickListener {
                val name = inputName.text?.toString() ?: ""
                val email = inputEmail.text?.toString() ?: ""
                val password = inputPassword.text?.toString() ?: ""
                val repeatPassword = inputRepeatPassword.text?.toString() ?: ""

                if (email.isBlank() || password.isBlank() || name.isBlank()) {
                    viewModel.onError(getString(R.string.empty_input))
                } else if (!email.matches(RegexValues.EMAIL_REGEX)) {
                    viewModel.onError(getString(R.string.invalid_email))
                } else if (password != repeatPassword) {
                    viewModel.onError(getString(R.string.password_dont_match))
                } else {
                    viewModel.doSignUp(email, password, name)
                }
            }

            btnMoveToSignIn.setOnClickListener {
                viewModel.onSignInBtnClicked()
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