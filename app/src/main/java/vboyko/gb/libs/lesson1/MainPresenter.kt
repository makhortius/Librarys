package vboyko.gb.libs.lesson1

import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.subjects.BehaviorSubject
import moxy.MvpPresenter
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainPresenter() : MvpPresenter<MainView>(), KoinComponent {

    private val router: Router by inject()

    private val screen: IScreens by inject()

    private val behaviorSubject = BehaviorSubject.create<TypeB>()


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
                else -> Unit
            }
        }
    }
}

enum class TypeB {
    BUTTON1,
    BUTTON2,
    BUTTON3,
}