package com.dicoding.storyapp.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.storyapp.R
import com.dicoding.storyapp.core.databinding.ItemRowStoryBinding
import com.dicoding.storyapp.core.domain.model.Story
import com.dicoding.storyapp.detail.DetailActivity

class StoriesFavoriteAdapter(private val onBookmarkClick: (Story) -> Unit) :
    ListAdapter<Story, StoriesFavoriteAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(binding: ItemRowStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val ivStoryImage = binding.ivItemPhoto
        val tvStoryName = binding.tvItemName
        val tvStoryDescription = binding.tvItemDescription
        val ivBookmark = binding.ivBookmark

        fun bind(story: Story) {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_STORY, story)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowStoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = getItem(position)

        Glide.with(holder.itemView.context).load(story.photoUrl).into(holder.ivStoryImage)

        holder.tvStoryName.text = story.name
        holder.tvStoryDescription.text = story.description

        val ivBookmark = holder.ivBookmark

        if (story.isBookmarked) {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(
                ivBookmark.context,
                R.drawable.baseline_favorite_48)
            )
        } else {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(
                ivBookmark.context,
                R.drawable.baseline_favorite_border_48)
            )
        }

        ivBookmark.setOnClickListener {
            onBookmarkClick(story)
        }

        holder.bind(story)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Story> =
            object : DiffUtil.ItemCallback<Story>() {
                override fun areItemsTheSame(
                    oldItem: Story,
                    newItem: Story
                ) : Boolean = oldItem.id == newItem.id

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: Story,
                    newItem: Story
                ) : Boolean = oldItem == newItem
            }
    }
}