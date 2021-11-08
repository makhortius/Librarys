package vboyko.gb.libs.lesson1

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import javax.inject.Inject

class OneUserPresenter @Inject constructor(
    private val router: Router,
    private val screens: IScreens,
) :
    MvpPresenter<OneUserView>() {

    init {
        App.instance.appComponent.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun backPressed(): Boolean {
        router.backTo(screen = screens.users())
        return true
    }

    fun updateUserInfo(userName: String, userAvatarUrl: String) {
        viewState.updateUserData(userName, userAvatarUrl)
    }
}