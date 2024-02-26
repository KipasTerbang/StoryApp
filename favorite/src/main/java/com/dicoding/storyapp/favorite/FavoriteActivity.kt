package com.dicoding.storyapp.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.storyapp.favorite.databinding.ActivityFavoriteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private val viewModel: FavoriteViewModel by viewModel()
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var storiesFavoriteAdapter: StoriesFavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(favoriteModule)

        supportActionBar?.title = "Favorite"
        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupAdapter()
        setupData()
        setData()
    }

    private fun setupAdapter() {
        storiesFavoriteAdapter = StoriesFavoriteAdapter { story ->
            if (story.isBookmarked) {
                viewModel.deleteStory(story)
            } else {
                viewModel.saveStory(story)
            }
        }
    }

    private fun setupData() {
        viewModel.getBookmarkedStories().observe(this) { bookmarkedStory ->
            if (bookmarkedStory.isEmpty()) {
                binding.tvMessage.visibility = View.VISIBLE
                storiesFavoriteAdapter.submitList(bookmarkedStory)
            } else {
                binding.tvMessage.visibility = View.GONE
                storiesFavoriteAdapter.submitList(bookmarkedStory)
            }
        }
    }

    private fun setData() {
        binding.rvStories.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = storiesFavoriteAdapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> true
        }
    }
}