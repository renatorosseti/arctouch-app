package com.arctouch.codechallenge.di

import com.arctouch.codechallenge.home.HomeActivity
import com.arctouch.codechallenge.home.HomeContracts
import dagger.Binds
import dagger.Module

@Module
abstract class BindActivityView {

    @Binds
    abstract fun providesOutput(activity: HomeActivity): HomeContracts.View
}