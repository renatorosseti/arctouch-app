package com.arctouch.application

import android.app.Application
import com.arctouch.di.Injector
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class CodeChallengeApp: Application(), KodeinAware {

    override lateinit var kodein: Kodein

    override fun onCreate() {
        super.onCreate()
        kodein = Injector().graph
    }
}
