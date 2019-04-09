package net.droidlabs.viking.mvp

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class Screen<V, P : Presenter<V>> protected constructor(private val layoutResId: Int, private val presenterClass: Class<P>) : DaggerAppCompatActivity() {

    private lateinit var presenterDelegate: PresenterDelegate<P>
    private lateinit var presenter: P

    @Inject
    internal lateinit var viewModelFactory: GenericViewModelFactory<P>

    fun presenter(): P? {
        return presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)

        presenter = ViewModelProviders.of(this, viewModelFactory).get(presenterClass)
        presenterDelegate = PresenterDelegate(presenter)

        onViewReady()

        if (savedInstanceState != null) {
            presenter.restoreState(savedInstanceState)
        }
    }

    public override fun onResume() {
        super.onResume()
        presenterDelegate.onStart()
    }

    public override fun onPause() {
        super.onPause()
        presenterDelegate.onStop()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenterDelegate!!.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenterDelegate.onSaveInstanceState(outState)
    }

    protected fun onViewReady() {

    }
}
