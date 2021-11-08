package vboyko.gb.libs.lesson1.di

import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import vboyko.gb.libs.lesson1.*
import vboyko.gb.libs.lesson1.database.AppDatabase
import vboyko.gb.libs.lesson1.database.UserDao
import javax.inject.Named
import javax.inject.Singleton

@Module
class CiceroneModule {

    private val cicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    fun cicerone(): Cicerone<Router> = cicerone


    @Provides
    @Singleton
    fun navigatorHolder(): NavigatorHolder = cicerone.getNavigatorHolder()

    @Provides
    @Singleton
    fun router(): Router = cicerone.router

    @Singleton
    @Provides
    fun screens(): IScreens = AndroidScreens()
}

@Module
class RepoModule {

    @Singleton
    @Provides
    fun usersRepo(client: GitHub, dao: UserDao): GithubUsersRepo = GithubUsersRepo(
        client, dao
    )
}

@Module
class ApiModule {

    @Provides
    @Named("baseUrl")
    fun baseUrl(): String = "https://api.github.com/"


    @Singleton
    @Provides
    fun gson(): Gson = GsonBuilder().create()

    @Provides
    fun api(
        @Named("baseUrl") baseUrl: String,
        gson: Gson,
        app: App
    ): GitHub = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(
            OkHttpClient.Builder()
                .addNetworkInterceptor(ChuckerInterceptor(app))
                .addNetworkInterceptor(StethoInterceptor())
                .build()
        )
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(GitHub::class.java)
}

@Module
class CacheModule {

    private val migrations = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE User ADD COLUMN age INTEGER DEFAULT 0 NOT NULL")
        }
    }

    @Singleton
    @Provides
    fun database(app: App): AppDatabase = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "gb-libs.db"
    )
        .addMigrations(migrations)
        .build()

    @Singleton
    @Provides
    fun userDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao()
}

@Module
class AppModule(private val app1: App) {

    @Provides
    fun app(): App {
        return app1
    }
}

@Singleton
@Component(
    modules = [
        AppModule::class,
        CiceroneModule::class,
        CacheModule::class,
        ApiModule::class,
        RepoModule::class
    ]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(usersPresenter: UsersPresenter)
    fun inject(usersPresenter: OneUserPresenter)
    fun inject(usersFragment: UsersFragment)
    fun inject(usersFragment: OneUserFragment)

}