package com.arctouch.codechallenge.di

import com.arctouch.codechallenge.repository.HomeInteractor
import com.arctouch.codechallenge.viewModel.HomeViewModel
import dagger.Module
import dagger.Provides

@Module
class HomeModelViewModule {

    @Provides
    fun providesHomeModelView(homeInteractor: HomeInteractor) = HomeViewModel(homeInteractor)
}