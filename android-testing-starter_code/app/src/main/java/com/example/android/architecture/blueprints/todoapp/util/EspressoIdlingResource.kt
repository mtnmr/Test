package com.example.android.architecture.blueprints.todoapp.util

import androidx.test.espresso.idling.CountingIdlingResource

//アプリがアイドル状態かどうかを追跡する
object EspressoIdlingResource {

    private const val RESOURCE = "GLOBAL"

    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)
    //アプリが何らかの作業を開始するたびに、カウンターをインクリメントし、その作業が終了したら、カウンターをデクリメントする
    //作業が行われていない場合のみカウンターが0になっている

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement(){
        if (!countingIdlingResource.isIdleNow){
            countingIdlingResource.decrement()
        }
    }
}

inline fun <T> wrapEspressoIdlingResource(function: () -> T): T {
    // Espresso does not work well with coroutines yet. See
    // https://github.com/Kotlin/kotlinx.coroutines/issues/982
    EspressoIdlingResource.increment() // Set app as busy.
    return try {
        function()
    } finally {
        EspressoIdlingResource.decrement() // Set app as idle.
    }
}

/*
EspressoIdlingResource.increment()
try {
    doSomethingThatTakesALongTime()
} finally {
    EspressoIdlingResource.decrement()
}

上のコードを下のように簡単にできるようにするためのインライン関数を作成した

wrapEspressoIdlingResource {
    doWorkThatTakesALongTime()
}
*/