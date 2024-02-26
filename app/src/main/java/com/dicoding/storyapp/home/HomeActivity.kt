package com.dicoding.storyapp.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.storyapp.R
import com.dicoding.storyapp.databinding.ActivityHomeBinding
import com.dicoding.storyapp.insert.InsertActivity
import com.dicoding.storyapp.main.MainActivity
import com.dicoding.storyapp.maps.MapsActivity
import com.dicoding.storyapp.setting.SettingActivity
import com.dicoding.storyapp.ui.LoadingStateAdapter
import com.dicoding.storyapp.ui.StoriesHomeAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("PrivatePropertyName")
class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModel()
    private val BACK_PRESSED_INTERVAL = 2000

    private var backPressedTime: Long = 0

    private lateinit var binding: ActivityHomeBinding
    private lateinit var storiesHomeAdapter: StoriesHomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.elevation = 0f

        setupAdapter()
        setupData()
        setData()
        setupAction()
    }

    private fun setupAction() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            setupData()
        }

        this.onBackPressedDispatcher.addCallback(this, object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (backPressedTime + BACK_PRESSED_INTERVAL > System.currentTimeMillis()) {
                    this@HomeActivity.finish()
                } else {
                    Toast.makeText(
                        this@HomeActivity,
                        R.string.press_back_again_to_exit,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                backPressedTime = System.currentTimeMillis()
            }
        })
    }

    private fun setData() {
        binding.rvStories.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = storiesHomeAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    storiesHomeAdapter.retry()
                }
            )
        }
    }

    private fun setupData() {
        viewModel.getLogin().observe(this) { user ->
            if (user.token.isNotBlank()) {
                executeGetAllStories(user.token)
            }
        }

        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun executeGetAllStories(token: String) {
        viewModel.getAllStories(token).observe(this) {
            storiesHomeAdapter.submitData(lifecycle, it)
        }
    }

    private fun setupAdapter() {
        storiesHomeAdapter = StoriesHomeAdapter { story ->
            if (story.isBookmarked) {
                viewModel.deleteStory(story)
            } else {
                viewModel.saveStory(story)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu_2, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_insert -> {
                startActivity(Intent(this, InsertActivity::class.java))
                true
            }
            R.id.menu_favorite -> {
                val uri = Uri.parse("storyapp://favorite")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
                true
            }
            R.id.menu_maps -> {
                startActivity(Intent(this, MapsActivity::class.java))
                true
            }
            R.id.menu_setting -> {
                startActivity(Intent(this, SettingActivity::class.java))
                true
            }
            R.id.menu_sign_out -> {
                showLogoutDialog()
                true
            }
            else -> true
        }
    }

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.sign_out)
            .setMessage(R.string.are_you_sure)
            .setPositiveButton(R.string.ok) { _, _ ->
                viewModel.deleteLogin()
                directToMainActivity()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }

        val alert = builder.create()
        alert.show()
    }

    private fun directToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}