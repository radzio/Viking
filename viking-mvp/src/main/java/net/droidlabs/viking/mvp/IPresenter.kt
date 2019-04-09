package net.droidlabs.viking.mvp

import android.content.Intent
import android.os.Bundle

interface IPresenter<TView> {

    fun start()

    fun stop()

    fun onResult(requestCode: Int, resultCode: Int, data: Intent?)

    fun saveState(dataWrapper: Bundle)

    fun restoreState(dataWrapper: Bundle)

    class DefaultPresenter : IPresenter<Any> {

        override fun start() {}

        override fun stop() {

        }

        override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {

        }

        override fun saveState(dataWrapper: Bundle) {

        }

        override fun restoreState(dataWrapper: Bundle) {

        }
    }
}