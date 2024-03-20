package com.a1danz.feature_authorization.presentation.screens.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.a1danz.feature_authorization.databinding.FragmentSignupBinding
import com.a1danz.feature_authorization.di.DaggerAuthComponent
import com.a1danz.feature_authorization.presentation.screens.vm_factory.SignUpViewModelFactory
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {
    private var _viewBinding: FragmentSignupBinding? = null
    private val viewBinding: FragmentSignupBinding get() = _viewBinding!!
    private lateinit var vmFactory: SignUpViewModelFactory
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
        val comp = DaggerAuthComponent.create()
        vmFactory = comp.getSignUpFactory()
        subscribe(getViewModel())

        initViews()
    }

    private fun initViews() {
        with(viewBinding) {
            btnSignup.setOnClickListener {
                val email = inputEmail.text?.toString() ?: ""
                val password = inputPassword.text?.toString() ?: ""
                val repeatPassword = inputRepeatPassword.text?.toString() ?: ""
                if (email.isBlank() || password.isBlank()) {
                    showError("Пустые данные")
                } else if (!email.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$"))) {
                    showError("E-mail невалидный")
                } else if (password != repeatPassword) {
                    showError("Пароли не совпадают")
                } else {
                    getViewModel().doSignUp(email, password)
                }
            }
        }
    }

    private fun getViewModel() = ViewModelProvider(this, vmFactory).get(SignUpViewModel::class.java)

    private fun showError(msg: String) {
        with(viewBinding) {
            tvError.text = msg
            tvError.visibility = View.VISIBLE
        }
    }

    private fun subscribe(viewModel: SignUpViewModel) {
        lifecycleScope.launch {
            viewModel.signUpResultFlow.collect { result ->
                if (result == viewModel.SUCCESS_SIGN_UP) {
                    showError("Успешная регистрация")
                } else if (result.isNotEmpty()) {
                    showError(result)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

}