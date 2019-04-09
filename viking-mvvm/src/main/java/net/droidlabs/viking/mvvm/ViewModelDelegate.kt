package net.droidlabs.viking.mvvm

import android.content.Intent
import android.os.Bundle

class ViewModelDelegate<VM : ViewModel>(
        private val viewModel: VM
) {

    fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            viewModel.restoreState(savedInstanceState)
        }
    }

    fun onStart() {
        viewModel.start()
    }

    fun onStop() {
        viewModel.stop()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        viewModel.onResult(requestCode, resultCode, data)
    }

    fun onSaveInstanceState(outState: Bundle) {
        viewModel.saveState(outState)
    }
}
