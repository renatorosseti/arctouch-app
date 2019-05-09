package com.arctouch.codechallenge.di

import com.arctouch.codechallenge.home.HomeContracts
import dagger.*

@Module
class BindOutputInteractor {

    @Provides
    fun providesOutput(output: HomePresenter): HomeContracts.InteractorOutput = output
}