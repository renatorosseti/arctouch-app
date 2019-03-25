package com.arctouch.codechallenge.di

import com.arctouch.ArctouchApp
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton
import dagger.android.AndroidInjector

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBuilder::class])
interface AppComponent : AndroidInjector<ArctouchApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<ArctouchApp>()


}