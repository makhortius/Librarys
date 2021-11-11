@file:Suppress("RemoveExplicitTypeArguments")

package vboyko.gb.libs.lesson1.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import vboyko.gb.libs.lesson1.*
import vboyko.gb.libs.lesson1.database.AppDatabase
import vboyko.gb.libs.lesson1.database.UserDao

fun startKoinApp(app: Context) = startKoin {
    androidContext(app)
    modules(
        listOf(
            ciceroneModule,
            repoModule,
            apiModule,
            cacheModule,
            someModule
        )
    )
}

val ciceroneModule = module {
    val cicerone: Cicerone<Router> = Cicerone.create()

    single<Cicerone<Router>> { cicerone }
    single<NavigatorHolder> { cicerone.getNavigatorHolder() }
    single<Router> { cicerone.router }
    single<IScreens> { AndroidScreens() }
}

val repoModule = module {
    single<GithubUsersRepo> { GithubUsersRepo(get(), get()) }
}

val apiModule = module {
    single<String>(qualifier = named("baseUrl")) { "https://api.github.com/" }
    single<String>(qualifier = named("test1")) { "test1" }
    single { GsonBuilder().create() }
    single<GitHub> {
        val baseUrl = get<String>(qualifier = named("baseUrl"))
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                OkHttpClient.Builder()
                    .addNetworkInterceptor(ChuckerInterceptor(get()))
                    .addNetworkInterceptor(StethoInterceptor())
                    .build()
            )
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
            .create(GitHub::class.java)
    }
}

val cacheModule = module {
    val migrations = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE User ADD COLUMN age INTEGER DEFAULT 0 NOT NULL")
        }
    }

    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "gb-libs.db"
        )
            .addMigrations(migrations)
            .build()
    }
    single<UserDao> { get<AppDatabase>().userDao() }
}

val someModule = module {
    single { OneUserPresenter(get(), get()) }
    single { UsersPresenter(get(), get(), get()) }
}