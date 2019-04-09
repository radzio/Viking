package net.droidlabs.viking.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class FragmentScreen<V, P : Presenter<V>> constructor(
        private val layoutResId: Int,
        private val presenterClass: Class<P>
) : DaggerFragment() {

    private lateinit var presenterDelegate: PresenterDelegate<P>
    private lateinit var presenter: P

    @Inject
    internal lateinit var viewModelFactory: GenericViewModelFactory<P>

    fun presenter(): P {
        return presenter
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(layoutResId, container, false)

        presenter = ViewModelProviders.of(this, viewModelFactory).get(presenterClass)
        presenterDelegate = PresenterDelegate(presenter)

        if (savedInstanceState != null) {
            presenterDelegate.onCreate(savedInstanceState)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewReady()
    }

    override fun onResume() {
        super.onResume()
        presenterDelegate.onStart()
    }

    override fun onPause() {
        super.onPause()
        presenterDelegate.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenterDelegate.onSaveInstanceState(outState)
    }

    protected fun onViewReady() {

    }
}
