package net.droidlabs.viking.rx.util

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer

interface CompositeDisposableContainer {
    fun Disposable.autoDispose()

    fun clear()
}

class AndroidCompositeDisposableContainer(
        private val compositeDisposable: CompositeDisposable = CompositeDisposable()
) : Disposable by compositeDisposable, DisposableContainer by compositeDisposable, CompositeDisposableContainer {
    override fun Disposable.autoDispose() {
        this@AndroidCompositeDisposableContainer.add(this)
    }

    override fun clear() {
        compositeDisposable.clear()
    }
}