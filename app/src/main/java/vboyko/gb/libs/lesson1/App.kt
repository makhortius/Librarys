package vboyko.gb.libs.lesson1

import android.app.Application
import com.facebook.stetho.Stetho
import vboyko.gb.libs.lesson1.di.AppComponent
import vboyko.gb.libs.lesson1.di.AppModule
import vboyko.gb.libs.lesson1.di.DaggerAppComponent

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        Stetho.initializeWithDefaults(this)
    }
}