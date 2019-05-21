package com.arctouch.codechallenge.di

import dagger.Module

@Module(includes = [
    HomeModelViewModule::class,
    SchedulerModule::class,
    BindActivityView::class,
    NetworkModule::class])
class AppModule