package com.dicoding.storyapp.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @field:ColumnInfo(name = "id")
    @PrimaryKey
    val id: String,

    @field:ColumnInfo("prev_key")
    val prevKey: Int?,

    @field:ColumnInfo("next_key")
    val nextKey: Int?
)