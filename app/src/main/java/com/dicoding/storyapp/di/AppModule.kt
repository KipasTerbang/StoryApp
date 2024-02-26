package com.dicoding.storyapp.di

import com.dicoding.storyapp.core.domain.usecase.story.StoryInteractor
import com.dicoding.storyapp.core.domain.usecase.story.StoryUseCase
import com.dicoding.storyapp.core.domain.usecase.storypaging.StoryPagingInteractor
import com.dicoding.storyapp.core.domain.usecase.storypaging.StoryPagingUseCase
import com.dicoding.storyapp.core.domain.usecase.user.UserInteractor
import com.dicoding.storyapp.core.domain.usecase.user.UserUseCase
import com.dicoding.storyapp.detail.DetailViewModel
import com.dicoding.storyapp.home.HomeViewModel
import com.dicoding.storyapp.insert.InsertViewModel
import com.dicoding.storyapp.main.MainViewModel
import com.dicoding.storyapp.maps.MapsViewModel
import com.dicoding.storyapp.setting.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<UserUseCase> { UserInteractor(get()) }
    factory<StoryUseCase> { StoryInteractor(get()) }
    factory<StoryPagingUseCase> { StoryPagingInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }
    viewModel { InsertViewModel(get(), get()) }
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { DetailViewModel(get(), get()) }
    viewModel { MapsViewModel(get(), get()) }
    viewModel { SettingViewModel(get(), get()) }
}