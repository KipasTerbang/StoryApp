package com.dicoding.storyapp.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "stories")
class StoryEntity(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey(autoGenerate = false)
    val id: String,

    @field:ColumnInfo(name = "name")
    val name: String,

    @field:ColumnInfo(name = "description")
    val description: String,

    @field:ColumnInfo(name = "photo_url")
    val photoUrl: String,

    @field:ColumnInfo(name = "created_at")
    val createdAt: String,

    @field:ColumnInfo(name = "lat")
    val lat: Double? = null,

    @field:ColumnInfo(name = "lon")
    val lon: Double? = null,

    @field:ColumnInfo(name = "is_bookmarked")
    var isBookmarked: Boolean
) : Parcelable