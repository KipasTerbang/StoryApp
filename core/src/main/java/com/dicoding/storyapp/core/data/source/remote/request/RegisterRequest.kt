package com.dicoding.storyapp.core.data.source.remote.request

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)