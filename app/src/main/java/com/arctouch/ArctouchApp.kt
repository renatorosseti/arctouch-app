package com.arctouch

import android.app.Activity
import android.app.Application
import com.arctouch.codechallenge.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class ArctouchApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out ArctouchApp> = DaggerAppComponent.builder().create(this)

}