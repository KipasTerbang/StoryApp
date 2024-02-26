package com.dicoding.storyapp.utils

import com.dicoding.storyapp.core.domain.model.Story

object DataDummy {
    fun generateDummyStoryEntity(): List<Story> {
        val items: MutableList<Story> = arrayListOf()
        for (i in 0..100) {
            val story = Story(
                "story-$i",
                "John Doe",
                "An image description.",
                "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/feature-1-kurikulum-global-3.png",
                "2022-02-22T22:22:22Z",
                0.0,
                0.0,
                false
            )
            items.add(story)
        }
        return items
    }
}