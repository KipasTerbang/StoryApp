package com.dicoding.storyapp.core.utils

import com.dicoding.storyapp.core.data.source.local.entity.StoryEntity
import com.dicoding.storyapp.core.data.source.remote.response.StoryResponse
import com.dicoding.storyapp.core.domain.model.Story

object StoryDataMapper {
    fun mapResponsesToEntities(input: List<StoryResponse>): List<StoryEntity> {
        val storyList = ArrayList<StoryEntity>()
        input.map {
            val story = StoryEntity(
                id = it.id,
                name = it.name,
                description = it.description,
                photoUrl = it.photoUrl,
                createdAt = it.createdAt,
                lat = it.lat,
                lon =it.lon,
                isBookmarked = false
            )
            storyList.add(story)
        }

        return storyList
    }

    fun mapEntitiesToDomain(input: List<StoryEntity>): List<Story> =
        input.map {
            Story(
                id = it.id,
                name = it.name,
                description = it.description,
                photoUrl = it.photoUrl,
                createdAt = it.createdAt,
                lat = it.lat,
                lon = it.lon,
                isBookmarked = it.isBookmarked
            )
        }

    fun mapDomainToEntity(input: Story) = StoryEntity(
        id = input.id,
        name = input.name,
        description = input.description,
        photoUrl = input.photoUrl,
        createdAt = input.createdAt,
        lat = input.lat,
        lon = input.lon,
        isBookmarked = input.isBookmarked
    )
}