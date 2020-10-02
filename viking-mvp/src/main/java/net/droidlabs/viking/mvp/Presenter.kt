package net.droidlabs.viking.mvp

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel

abstract class Presenter<TView> : ViewModel(), IPresenter<TView> {

    private val onResultActions = SparseArray<OnResultAction>()
    private val startupActions = ArrayList<StartupAction>()

    private var childPresenter: IPresenter<*>? = null
    private var started = false
    private var stateWasRestored: Boolean = false

    private val isFirstRun: Boolean
        get() = !started

    @CallSuper
    override fun start() {
        if (childPresenter != null) {
            childPresenter!!.start()
        }

        if (isFirstRun) {
            for (action in startupActions) {
                action.execute()
            }
            started = true
        }
    }

    @CallSuper
    override fun stop() {
        if (childPresenter != null) {
            childPresenter!!.stop()
        }
    }

    @CallSuper
    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
        executeOnResultActions(requestCode, data, resultCode == RESULT_OK)
    }

    @CallSuper
    override fun saveState(dataWrapper: Bundle) {

    }

    @CallSuper
    override fun restoreState(dataWrapper: Bundle) {
        stateWasRestored = true
    }

    fun registerOnResultAction(requestCode: Int, action: OnResultAction) {
        onResultActions.put(requestCode, action)
    }

    fun stateWasRestored(): Boolean {
        return stateWasRestored
    }

    protected fun runOnStartup(action: StartupAction) {
        if (started) {
            action.execute()
        } else {
            startupActions.add(action)
        }
    }

    protected fun changePresentation(childPresenter: IPresenter<*>) {
        if (this.childPresenter != null && started) {
            this.childPresenter!!.stop()
        }

        this.childPresenter = childPresenter

        if (this.childPresenter != null && started) {
            this.childPresenter!!.start()
        }
    }

    private fun executeOnResultActions(requestCode: Int, data: Intent?, result: Boolean) {
        val onResultAction = onResultActions.get(requestCode)
        if (onResultAction != null) {
            onResultActions.get(requestCode).execute(result, data)
        }
    }
}