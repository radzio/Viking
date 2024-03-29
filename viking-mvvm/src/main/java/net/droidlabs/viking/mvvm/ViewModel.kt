package net.droidlabs.viking.mvvm

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import androidx.annotation.CallSuper
import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import kotlin.properties.ObservableProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

typealias OnValueChange<T> = (T) -> Unit

abstract class ViewModel(
        private val observable: BaseObservable = BaseObservable()
) : androidx.lifecycle.ViewModel(), Observable by observable {

    private val onResultActions = SparseArray<OnResultAction>()
    private val startupActions = mutableListOf<StartupAction>()
    private val childViewModels = mutableListOf<ViewModel>()

    private var stateWasRestored: Boolean = false
    private var started = false

    private val isFirstRun: Boolean
        get() = !started

    @CallSuper
    open fun start() {
        Log.d("TAG", "start")
        if (isFirstRun) {
            for (action in startupActions) {
                action.execute()
            }
            started = true
        }

        for (i in childViewModels.indices) {
            childViewModels[i].start()
        }
    }

    @CallSuper
    open fun stop() {
        for (i in childViewModels.indices) {
            childViewModels[i].stop()
        }
    }

    @CallSuper
    fun registerChildViewModel(viewModel: ViewModel) {
        if (!childViewModels.contains(viewModel)) {
            childViewModels.add(viewModel)
        }
    }

    @CallSuper
    fun unRegisterChildViewModel(viewModel: ViewModel) {
        childViewModels.remove(viewModel)
    }

    @CallSuper
    fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
        executeOnResultActions(requestCode, data, resultCode == RESULT_OK)
    }

    @CallSuper
    fun saveState(outState: Bundle) {
        for (i in childViewModels.indices) {
            childViewModels[i].saveState(outState)
        }
    }

    @CallSuper
    fun restoreState(savedState: Bundle) {
        for (i in childViewModels.indices) {
            childViewModels[i].restoreState(savedState)
        }
        stateWasRestored = true
    }

    fun runOnStartup(action: StartupAction) {
        if (wasAlreadyStarted()) {
            action.execute()
        } else {
            startupActions.add(action)
        }
    }

    fun registerOnResultAction(requestCode: Int, action: OnResultAction) {
        onResultActions.put(requestCode, action)
    }

    fun stateWasRestored(): Boolean {
        return stateWasRestored
    }
    //region observable
    @Transient
    private var mCallbacks: PropertyChangeRegistry? = null

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (mCallbacks == null) {
                mCallbacks = PropertyChangeRegistry()
            }
        }
        mCallbacks!!.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.remove(callback)
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    fun notifyChange() {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with [Bindable] to generate a field in
     * `BR` to be used as `fieldId`.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        synchronized(this) {
            if (mCallbacks == null) {
                return
            }
        }
        mCallbacks!!.notifyCallbacks(this, fieldId, null)
    }
    //endregion

    private fun wasAlreadyStarted(): Boolean {
        return started
    }

    private fun executeOnResultActions(requestCode: Int?, data: Intent?, result: Boolean) {
        val onResultAction = onResultActions.get(requestCode!!)
        if (onResultAction != null) {
            onResultActions.get(requestCode).execute(result, data)
        }
    }

    /**
     * Extention method for creating kotlin delegated properties which automatically notify [BaseObservable] that
     * property value has changed
     */
    fun <T> BaseObservable.observable(initialValue: T, br: Int, onValueChange: OnValueChange<T> = {})
            : ReadWriteProperty<Any?, T> = object : ObservableProperty<T>(initialValue) {
        override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) {
            if (oldValue != newValue) {
                this@observable.notifyPropertyChanged(br)
                onValueChange(newValue)
            }
        }
    }

    /**
     * Extention method for creating kotlin delegated properties which automatically notify [BaseViewModel] that
     * property value has changed
     */
    fun <T> ViewModel.observable(initialValue: T, br: Int, onValueChange: OnValueChange<T> = {})
            : ReadWriteProperty<Any?, T> = object : ObservableProperty<T>(initialValue) {
        override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) {
            if (oldValue != newValue) {
                this@observable.notifyPropertyChanged(br)
                onValueChange(newValue)
            }
        }
    }
}
