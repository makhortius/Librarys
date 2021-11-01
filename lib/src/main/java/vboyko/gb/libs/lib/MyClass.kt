package vboyko.gb.libs.lib

import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit


class MyClass {
    fun observableTake(): @NonNull Observable<String> {
        val interval = Observable.interval(2, TimeUnit.SECONDS)
            .map { "сейчас время $it" }
        val observable = Observable.just("Hello", "from", "msk")
        return Observable.combineLatest(interval, observable, { t1, t2 ->
            "number = $t1 text = $t2"
        })
            .map { it.toString() }
    }


}

class Consumer( observable: Observable<String>) {
    private val observer = object : Observer<String> {
        override fun onSubscribe(d: Disposable) {
            println("onSubscribe")
        }

        override fun onNext(t: String) {
            println("onNext $t")
        }

        override fun onError(e: Throwable) {
            println("onError ${e.message}")
        }

        override fun onComplete() {
            println("onComplete")
        }
    }

    init {
        observable.subscribe(observer)
    }
}

fun main() {
    val observableTake = MyClass().observableTake()
    Consumer(observableTake)

    Thread.sleep(20000)
}


data class Test1(
    val something: String? = null
)
