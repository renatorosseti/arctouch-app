package com.arctouch.codechallenge.di

import com.arctouch.codechallenge.home.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [AppModule::class])
    abstract fun contributeHomeActivity(): HomeActivity
}