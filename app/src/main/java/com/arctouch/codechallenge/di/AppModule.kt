package com.arctouch.codechallenge.di

import dagger.Module

@Module(includes = [BindOutputInteractor::class,
    HomeModelViewModule::class,
    SchedulerModule::class,
    BindActivityView::class,
    NetworkModule::class])
class AppModule