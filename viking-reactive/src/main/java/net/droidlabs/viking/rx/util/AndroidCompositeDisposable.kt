package net.droidlabs.viking.rx.util

class AndroidCompositeDisposable(
        private val compositeDisposableContainer: CompositeDisposableContainer = AndroidCompositeDisposableContainer()
) : AndroidDisposable, CompositeDisposableContainer by compositeDisposableContainer {

    override fun onLifecycleStop() {
        this.clear()
    }
}