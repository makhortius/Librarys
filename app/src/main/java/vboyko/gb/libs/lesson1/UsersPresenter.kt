package vboyko.gb.libs.lesson1

import android.util.Log
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import moxy.MvpPresenter

class UsersPresenter(val usersRepo: GithubUsersRepo, val router: Router) :
    MvpPresenter<UsersView>() {
    class UsersListPresenter : IUserListPresenter {

        val users = (1..20).map { GithubUser("login $it", 0) }.toMutableList()

        override var itemClickListener: ((UserItemView) -> Unit)? = null
        override fun getCount() = users.size
        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
        }
    }

    val usersListPresenter = UsersListPresenter()

    private val disposable = usersRepo.subscribeOnGithubUsersData()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            usersListPresenter.users.clear()
            usersListPresenter.users.addAll(it)
            viewState.updateList()
        }, {
            Log.e("GB_LIBS", it.message, it)
        })

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        usersRepo.loadUserData()
        usersListPresenter.itemClickListener = { itemView ->
//TODO: переход на экран пользователя
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }


    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}