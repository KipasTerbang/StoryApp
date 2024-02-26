package com.dicoding.storyapp.main

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.dicoding.storyapp.R
import com.dicoding.storyapp.core.data.source.local.entity.UserEntity
import com.dicoding.storyapp.core.data.source.remote.network.ApiResponse
import com.dicoding.storyapp.core.data.source.remote.request.LoginRequest
import com.dicoding.storyapp.core.data.source.remote.response.LoginResult
import com.dicoding.storyapp.databinding.FragmentLoginBinding
import com.dicoding.storyapp.insert.InsertActivity
import com.dicoding.storyapp.setting.SettingActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("PrivatePropertyName")
class LoginFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()
    private val BACK_PRESSED_INTERVAL = 2000

    private var backPressedTime: Long = 0

    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.ivAccount, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }

    private fun setupAction() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (backPressedTime + BACK_PRESSED_INTERVAL > System.currentTimeMillis()) {
                    requireActivity().finish()
                } else {
                    Toast.makeText(
                        requireContext(),
                        R.string.press_back_again_to_exit,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                backPressedTime = System.currentTimeMillis()
            }
        })

        binding.apply {
            edLoginPassword.setOnEditorActionListener { _, actionId, _ ->
                clearFocusOnDoneAction(actionId)
            }

            cbShowPassword.setOnCheckedChangeListener { _, isChecked ->
                toggleLoginPasswordVisibility(isChecked)
            }

            tvSignUp.setOnClickListener { moveToRegisterFragment() }

            btnSignIn.setOnClickListener {
                val email = edLoginEmail.text.toString()
                val password = edLoginPassword.text.toString()

                login(email, password)
            }
        }
    }

    private fun login(email: String, password: String) {
       binding.apply {
           when {
               email.isEmpty() -> {
                   edLoginEmail.error = R.string.please_fill_the_email.toString()
               }
               password.isEmpty() -> {
                   edLoginPassword.error = R.string.please_fill_the_password.toString()
               }
               else -> {
                   executeLogin(email, password)
               }
           }
       }
    }

    private fun executeLogin(email: String, password: String) {
        binding.apply {
            viewModel.login(LoginRequest(email, password)).observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when (result) {
                        is ApiResponse.Empty -> {
                            progressBar.visibility = View.VISIBLE
                            btnSignIn.isEnabled = false
                        }
                        is ApiResponse.Success -> {
                            progressBar.visibility = View.GONE
                            btnSignIn.isEnabled = true
                            Toast.makeText(
                                context,
                                R.string.sign_in_success,
                                Toast.LENGTH_SHORT
                            ).show()

                            setLogin(result.data.loginResult)
                        }
                        is ApiResponse.Error -> {
                            progressBar.visibility = View.GONE
                            btnSignIn.isEnabled = true
                            Toast.makeText(
                                context,
                                R.string.sign_in_failed,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun moveToRegisterFragment() {
        val registerFragment = RegisterFragment()
        val fragmentManager = parentFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(R.id.frame_container, registerFragment, RegisterFragment::class.java.simpleName)
            addToBackStack(null)
            commit()
        }
    }

    private fun toggleLoginPasswordVisibility(isChecked: Boolean) {
        binding.apply {
            val selection = edLoginPassword.selectionEnd

            if (isChecked) {
                edLoginPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                edLoginPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }

            edLoginPassword.setSelection(selection)
        }
    }

    private fun clearFocusOnDoneAction(actionId: Int) : Boolean {
        binding.apply {
            val imm = requireContext().getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                edLoginPassword.clearFocus()
                imm.hideSoftInputFromWindow(edLoginPassword.windowToken, 0)
                return true
            }

            return false
        }
    }

    private fun setLogin(loginResult: LoginResult) {
        loginResult.apply { viewModel.setLogin(UserEntity(userId, name, token)) }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu_1, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_insert -> {
                startActivity(Intent(context, InsertActivity::class.java))
                true
            }
            R.id.menu_setting -> {
                startActivity(Intent(context, SettingActivity::class.java))
                true
            }
            else -> true
        }
    }
}