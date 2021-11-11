package vboyko.gb.libs.lesson1

import android.app.Application
import com.facebook.stetho.Stetho
import org.koin.core.KoinApplication
import vboyko.gb.libs.lesson1.di.startKoinApp

class App : Application() {

    companion object {
        lateinit var instance: App
        lateinit var koinApp: KoinApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        koinApp = startKoinApp(instance)
        Stetho.initializeWithDefaults(this)
    }
}