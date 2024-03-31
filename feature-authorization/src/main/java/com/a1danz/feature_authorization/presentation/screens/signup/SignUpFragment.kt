package com.a1danz.feature_authorization.presentation.screens.signup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.a1danz.common.core.config.RegexValues
import com.a1danz.common.di.featureprovide.FeatureContainer
import com.a1danz.feature_authorization.AuthorizationRouter
import com.a1danz.feature_authorization.R
import com.a1danz.feature_authorization.databinding.FragmentSignupBinding
import com.a1danz.feature_authorization.di.AuthComponent
import com.a1danz.feature_authorization.di.DaggerAuthComponent
import com.a1danz.feature_authorization.presentation.screens.AuthorizationFragment
import com.a1danz.feature_authorization.presentation.screens.vm_factory.SignUpViewModelFactory
import com.a1danz.feature_authorization.utils.AuthorizationCodes
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpFragment : AuthorizationFragment() {
    private var _viewBinding: FragmentSignupBinding? = null
    private val viewBinding: FragmentSignupBinding get() = _viewBinding!!

    @Inject lateinit var vmFactory: ViewModelProvider.Factory
    private val viewModel : SignUpViewModel by viewModels { vmFactory }

    @Inject lateinit var router : AuthorizationRouter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as FeatureContainer).getFeature(
            AuthComponent::class.java
        ).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _viewBinding = FragmentSignupBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe(  )
        initViews()
    }

    private fun initViews() {
        with(viewBinding) {
            btnSignup.setOnClickListener {
                val email = inputEmail.text?.toString() ?: ""
                val password = inputPassword.text?.toString() ?: ""
                val repeatPassword = inputRepeatPassword.text?.toString() ?: ""
                if (email.isBlank() || password.isBlank()) {
                    showError(getString(R.string.empty_input))
                } else if (!email.matches(RegexValues.EMAIL_REGEX)) {
                    showError(getString(R.string.invalid_email))
                } else if (password != repeatPassword) {
                    showError(getString(R.string.password_dont_match))
                } else {
                    viewModel.doSignUp(email, password)
                }
            }

            btnMoveToRegister.setOnClickListener {
                moveToSignIn()
            }
        }
    }


    private fun showError(msg: String) {
        with(viewBinding) {
            tvError.text = msg
            tvError.visibility = View.VISIBLE
        }
    }

    private fun subscribe() {
        lifecycleScope.launch {
            viewModel.signUpResultFlow.collect { result ->
                if (result == AuthorizationCodes.SUCCESS_AUTH_CODE) {
                    moveToNext()
                } else if (result.isNotEmpty()) {
                    showError(result)
                }
            }
        }
    }

    fun moveToSignIn() {
        router.openSignInScreen()
    }

    fun moveToNext() {
        router.openMainScreen()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}