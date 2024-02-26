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
import androidx.fragment.app.Fragment
import com.dicoding.storyapp.R
import com.dicoding.storyapp.core.data.source.remote.network.ApiResponse
import com.dicoding.storyapp.core.data.source.remote.request.RegisterRequest
import com.dicoding.storyapp.databinding.FragmentRegisterBinding
import com.dicoding.storyapp.insert.InsertActivity
import com.dicoding.storyapp.setting.SettingActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
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
        binding.apply {
            edRegisterPassword.apply {
                setOnEditorActionListener { _, actionId, _ -> clearFocusOnDoneAction(actionId) }
            }

            cbShowPassword.setOnCheckedChangeListener { _, isChecked ->
                toggleLoginPasswordVisibility(isChecked)
            }

            tvSignIn.setOnClickListener { moveToLoginFragment() }

            btnSignUp.setOnClickListener {
                val name = edRegisterName.text.toString()
                val email = edRegisterEmail.text.toString()
                val password = edRegisterPassword.text.toString()

                register(name, email, password)
            }
        }
    }

    private fun clearFocusOnDoneAction(actionId: Int) : Boolean {
        binding.apply {
            val imm = requireContext().getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                edRegisterPassword.clearFocus()
                edRegisterPassword.error = null
                imm.hideSoftInputFromWindow(edRegisterPassword.windowToken, 0)
                return true
            }

            return false
        }
    }

    private fun toggleLoginPasswordVisibility(isChecked: Boolean) {
        binding.apply {
            val selection = edRegisterPassword.selectionEnd

            if (isChecked) {
                edRegisterPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                edRegisterPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }

            edRegisterPassword.setSelection(selection)
            edRegisterPassword.error = null
        }
    }

    private fun register(name: String, email: String, password: String) {
        binding.apply {
            when {
                name.isEmpty() -> {
                    edRegisterName.error = R.string.please_fill_your_name.toString()
                }
                email.isEmpty() -> {
                    edRegisterEmail.error = R.string.please_fill_the_email.toString()
                }
                password.isEmpty() -> {
                    edRegisterPassword.error = R.string.please_fill_the_password.toString()
                }
                password.length < 8 -> {
                    edRegisterPassword.error = R.string.password_must_be_at_least_8_character.toString()
                }
                else -> {
                    executeRegister(name, email, password)
                }
            }
        }
    }

    private fun executeRegister(name: String, email: String, password: String) {
        binding.apply {
            viewModel.register(
                RegisterRequest(name, email, password)
            ).observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when (result) {
                        is ApiResponse.Empty -> {
                            progressBar.visibility = View.VISIBLE
                            btnSignUp.isEnabled = false
                        }
                        is ApiResponse.Success -> {
                            progressBar.visibility = View.GONE
                            btnSignUp.isEnabled = true
                            Toast.makeText(context,
                                R.string.create_an_account_success,
                                Toast.LENGTH_SHORT
                            ).show()
                            moveToLoginFragment()
                        }
                        is ApiResponse.Error -> {
                            btnSignUp.isEnabled = true
                            Toast.makeText(context,
                                R.string.create_an_account_failed,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    private fun moveToLoginFragment() {
        val fragmentManager = parentFragmentManager
        fragmentManager.popBackStack()
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