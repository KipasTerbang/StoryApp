package com.dicoding.storyapp.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.storyapp.R
import com.dicoding.storyapp.databinding.ActivityMainBinding
import com.dicoding.storyapp.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initTheme()
        setupFragment()
    }

    private fun setupFragment() {
        viewModel.getLogin().observe(this) { user ->
            if (user.token.isNotBlank()) {
                directToHomeActivity()
            } else {
                addLoginFragment()
            }
        }
    }

    private fun addLoginFragment() {
        val fragmentManager = supportFragmentManager
        val loginFragment = LoginFragment()
        val fragment = fragmentManager.findFragmentByTag(LoginFragment::class.java.simpleName)

        if (fragment !is LoginFragment) {
            Log.d("StoryApp", "Fragment Name: ${LoginFragment::class.java.simpleName}")
            fragmentManager
                .beginTransaction()
                .replace(R.id.frame_container, loginFragment, LoginFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun directToHomeActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun initTheme() {
        viewModel.getThemeSetting()
    }
}