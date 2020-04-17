package net.droidlabs.viking.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerAppCompatDialogFragment
import javax.inject.Inject

abstract class DialogFragmentView<VM : ViewModel, VD : ViewDataBinding>(
    @param:LayoutRes private val layoutIdRes: Int,
    private val viewModelClass: Class<VM>
) : DaggerAppCompatDialogFragment() {

  private lateinit var viewModel: VM
  private lateinit var binding: VD
  private lateinit var viewModelDelegate: ViewModelDelegate<VM>

  @Inject
  internal lateinit var viewModelFactory: GenericViewModelFactory<VM>

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(layoutIdRes, container, false)

    viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
    viewModelDelegate = ViewModelDelegate(viewModel)
    binding = DataBindingUtil.bind(view)!!

    bind(binding)

    if (savedInstanceState != null) {
      viewModelDelegate.onCreate(savedInstanceState)
    }

    return view
  }

  override fun onResume() {
    super.onResume()
    viewModelDelegate.onStart()
  }

  override fun onPause() {
    super.onPause()
    viewModelDelegate.onStop()
  }

  fun viewModel(): VM {
    return viewModel
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    viewModelDelegate.onSaveInstanceState(outState)
  }

  inline fun <reified T : FragmentView<*, *>> resolveFragment(@IdRes id: Int): T? {
    val fragment = childFragmentManager.findFragmentById(id)
    return if (fragment is T) {
      fragment
    } else {
      null
    }
  }

  inline fun <reified T : ViewModel> resolveChildViewModel(@IdRes id: Int): T? {
    val fragment = resolveFragment<FragmentView<*, *>>(id)

    return if (fragment == null) {
      null
    } else {
      if (fragment.viewModel() is T) {
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
