package net.droidlabs.viking.mvvm

import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class ActivityView<VM : ViewModel, VD : ViewDataBinding>(
        @param:LayoutRes private val layoutIdRes: Int,
        private val viewModelClass: Class<VM>
) : DaggerAppCompatActivity() {


    private lateinit var viewModel: VM
    private lateinit var binding: VD
    private lateinit var viewModelDelegate: ViewModelDelegate<VM>

    @Inject
    internal lateinit var viewModelFactory: GenericViewModelFactory<VM>

    fun viewModel(): VM {
        return viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
        viewModelDelegate = ViewModelDelegate<VM>(viewModel)
        binding = DataBindingUtil.setContentView(this, layoutIdRes)
        bind(binding)

        viewModelDelegate.onCreate(savedInstanceState)
    }

    public override fun onResume() {
        super.onResume()
        viewModelDelegate.onStart()
    }

    public override fun onPause() {
        super.onPause()
        viewModelDelegate.onStop()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModelDelegate.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModelDelegate.onSaveInstanceState(outState)
    }

    inline fun  <reified T : FragmentView<*, *>> resolveFragment(@IdRes id: Int): T? {
        val fragment = supportFragmentManager.findFragmentById(id)
        return if(fragment is T) {
            fragment
        } else {
            null
        }
    }

    inline fun <reified T : ViewModel> resolveChildViewModel(@IdRes id: Int): T? {
        val fragment = resolveFragment<FragmentView<*, *>>(id)

        return if(fragment == null) {
            null
        } else {
            if(fragment.viewModel() is T) {
                fragment.viewModel() as T
            } else {
                null
            }
        }
    }

    protected fun binding(): VD {
        return binding
    }

    protected abstract fun bind(binding: VD)
}
