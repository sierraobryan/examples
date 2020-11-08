package com.example.koin_simple.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

open class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        if (GlobalContext.getOrNull() == null) {

            startKoin {
                // inject Android context
                androidContext(this@MainApplication)
                // use properties from assets/koin.properties
                androidFileProperties()
                // use modules
                modules(modules)
            }
        }
    }
}


