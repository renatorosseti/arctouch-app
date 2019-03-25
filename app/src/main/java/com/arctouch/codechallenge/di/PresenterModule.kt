package com.arctouch.codechallenge.di

import com.arctouch.codechallenge.home.HomeActivity
import com.arctouch.codechallenge.home.HomeContracts
import com.arctouch.codechallenge.home.HomeInteractor
import com.arctouch.codechallenge.home.HomePresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    fun providesHomePresenter(view: HomeContracts.View, interactor: HomeInteractor) = HomePresenter(view, interactor)
}