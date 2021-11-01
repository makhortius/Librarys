package vboyko.gb.libs.lesson1

import io.reactivex.rxjava3.core.Observable

class GithubUsersRepo {

    private val client = RetrofitKeeper().api

    fun getUsers2(): Observable<List<GithubUser>> = client.loadUsers().toObservable()
}