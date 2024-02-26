package com.dicoding.storyapp.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @field:ColumnInfo(name = "userId")
    @field:PrimaryKey(autoGenerate = false)
    val userId: String,

    @field:ColumnInfo(name = "name")
    val name: String,

    @field:ColumnInfo("token")
    val token: String
)