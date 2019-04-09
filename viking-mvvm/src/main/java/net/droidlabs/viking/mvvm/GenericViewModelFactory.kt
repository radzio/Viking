package net.droidlabs.viking.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import javax.inject.Inject
import javax.inject.Provider

class GenericViewModelFactory<T : ViewModel>
@Inject constructor(
        private val viewModelProvider: Provider<T>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        try {
            return viewModelProvider.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}