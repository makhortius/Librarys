package vboyko.gb.libs.lesson1

import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.subjects.BehaviorSubject
import moxy.MvpPresenter

class MainPresenter(val router: Router, val screen: IScreens) : MvpPresenter<MainView>() {

    val behaviorSubject = BehaviorSubject.create<TypeB>()


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(screen.users())
    }

    fun backClicked() {
        behaviorSubject.onNext(TypeB.BUTTON1)
        router.exit()
    }

    init {
        behaviorSubject.onNext(TypeB.BUTTON2)
        behaviorSubject.subscribe {
            when (it) {
                TypeB.BUTTON1 -> Unit
                TypeB.BUTTON2 -> Unit
                TypeB.BUTTON3 -> Unit
            }


        }
    }
}

enum class TypeB {
    BUTTON1,
    BUTTON2,
    BUTTON3,
}