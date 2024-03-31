package com.a1danz.feature_authorization.presentation.screens.signin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.a1danz.common.core.config.RegexValues
import com.a1danz.common.core.resources.ResourceManager
import com.a1danz.common.di.featureprovide.FeatureContainer
import com.a1danz.feature_authorization.AuthorizationRouter
import com.a1danz.feature_authorization.R
import com.a1danz.feature_authorization.databinding.FragmentSigninBinding
import com.a1danz.feature_authorization.di.AuthComponent
import com.a1danz.feature_authorization.presentation.screens.AuthorizationFragment
import com.a1danz.feature_authorization.presentation.screens.vm_factory.SignInViewModelFactory
import com.a1danz.feature_authorization.utils.AuthorizationCodes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInFragment : AuthorizationFragment() {
    private var _viewBinding : FragmentSigninBinding? = null
    private val viewBinding : FragmentSigninBinding get() = _viewBinding!!

    @Inject lateinit var vmFactory : ViewModelProvider.Factory
    private val viewModel : SignInViewModel by viewModels { vmFactory }
    @Inject lateinit var router : AuthorizationRouter
    @Inject lateinit var resourceManager: ResourceManager


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
        _viewBinding = FragmentSigninBinding.inflate(inflater)
        return viewBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribe(viewModel)
        initViews()
    }

    private fun subscribe(viewModel: SignInViewModel) {
        lifecycleScope.launch {
            viewModel.signInResultFlow.collect {
                if (it == AuthorizationCodes.SUCCESS_AUTH_CODE) {
                    moveToNext()
                } else if (it.isNotEmpty()) {
                    showError(it)
                }
            }
        }
    }

    private fun initViews() {
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
                moveToSignUp()
            }
        }
    }

    fun moveToNext() {
        router.openMainScreen()
    }

    private fun showError(msg : String) {
        with(viewBinding) {
            tvError.text = msg
            tvError.visibility = View.VISIBLE
        }
    }

    private fun moveToSignUp() {
        router.openSignUpScreen()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}