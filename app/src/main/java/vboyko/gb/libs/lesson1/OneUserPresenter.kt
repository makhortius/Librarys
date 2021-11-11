package vboyko.gb.libs.lesson1

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import org.koin.core.qualifier.named
import org.koin.dsl.module

class OneUserPresenter constructor(
    private val router: Router,
    private val screens: IScreens,
) :
    MvpPresenter<OneUserView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
    }

    fun backPressed(): Boolean {
        router.backTo(screen = screens.users())
        return true
    }

    fun updateUserInfo(userName: String, userAvatarUrl: String) {
        val list = listOf(
            module {
                single(named("test55")) { "hello from runtime" }
            }
        )
        viewState.updateUserData(userName, userAvatarUrl)
    }
}