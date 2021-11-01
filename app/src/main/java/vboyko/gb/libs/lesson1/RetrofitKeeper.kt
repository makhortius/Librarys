package vboyko.gb.libs.lesson1

import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class RetrofitKeeper {
    private val gson = Gson()

    private val retrofit = Retrofit.Builder().baseUrl("https://api.github.com/")
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val api = retrofit.create(GitHub::class.java)
}

interface GitHub {

    @GET("users")
    fun loadUsers(): Single<List<GithubUser>>
}