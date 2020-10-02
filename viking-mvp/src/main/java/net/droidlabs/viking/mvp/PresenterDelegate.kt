package net.droidlabs.viking.mvp

import android.content.Intent
import android.os.Bundle

class PresenterDelegate<P : Presenter<*>>(private val presenter: P) {

     fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            presenter.restoreState(savedInstanceState)
        }
    }

    fun onStart() {
        presenter.start()
    }

     fun onStop() {
        presenter.stop()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.onResult(requestCode, resultCode, data)
    }

    fun onSaveInstanceState(outState: Bundle) {
        presenter.saveState(outState)
    }
}
