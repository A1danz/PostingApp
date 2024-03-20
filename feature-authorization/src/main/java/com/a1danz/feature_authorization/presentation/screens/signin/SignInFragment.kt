package com.a1danz.feature_authorization.presentation.screens.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.a1danz.feature_authorization.databinding.FragmentSigninBinding
import com.a1danz.feature_authorization.di.DaggerAuthComponent
import com.a1danz.feature_authorization.presentation.screens.vm_factory.SignInViewModelFactory
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {
    private var _viewBinding : FragmentSigninBinding? = null
    private val viewBinding : FragmentSigninBinding get() = _viewBinding!!

    lateinit var vmFactory : SignInViewModelFactory
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
        val authComp = DaggerAuthComponent.create()
        vmFactory = authComp.getSignInFactory()
        subscribe(getViewModel())
        initViews()
    }

    private fun subscribe(viewModel: SignInViewModel) {
        lifecycleScope.launch {
            viewModel.signInResultFlow.collect {
                if (it == viewModel.SUCCESS_SIGN_IN) {
                    showError("Авторизация прошла успешно")
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
                    showError("Пустые данные")
                } else if (!email.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$"))) {
                    showError("E-mail невалидный")
                } else {
                    getViewModel().doSignIn(email, password)
                }

            }
        }
    }

    private fun getViewModel() = ViewModelProvider(this, vmFactory).get(SignInViewModel::class.java)

    private fun showError(msg : String) {
        with(viewBinding) {
            tvError.text = msg
            tvError.visibility = View.VISIBLE
        }
    }

    private fun moveToSignUp() {
        // TODO: realize
        showError("Not realised :(")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}