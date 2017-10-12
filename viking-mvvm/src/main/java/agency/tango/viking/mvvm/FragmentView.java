package agency.tango.viking.mvvm;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public abstract class FragmentView<VM extends ViewModel, VD extends ViewDataBinding> extends
    DaggerFragment {

  private VM viewModel;
  private VD binding;
  private int layoutIdRes;
  private final Class<VM> viewModelClass;
  private ViewModelDelegate<VM> viewModelDelegate;

  @Inject
  ViewModelProvider.Factory viewModelFactory;

  public FragmentView(@LayoutRes int layoutIdRes, Class<VM> viewModelClass) {
    this.layoutIdRes = layoutIdRes;
    this.viewModelClass = viewModelClass;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
      Bundle savedInstanceState) {
    View view = inflater.inflate(layoutIdRes, container, false);

    viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass);
    viewModelDelegate = new ViewModelDelegate<>(viewModel);
    binding = DataBindingUtil.bind(view);

    bind(binding);

    if (savedInstanceState != null) {
      viewModelDelegate.onCreate(savedInstanceState);
    }

    return view;
  }

  @Override
  public void onStart() {
    super.onStart();
    viewModelDelegate.onStart();
  }

  @Override
  public void onStop() {
    viewModelDelegate.onStop();
    super.onStop();
  }

  public VM viewModel() {
    return viewModel;
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    viewModelDelegate.onSaveInstanceState(outState);
  }

  @SuppressWarnings("unchecked")
  protected <T extends FragmentView> T resolveFragment(@IdRes int id) {
    return (T) getChildFragmentManager().findFragmentById(id);
  }

  @SuppressWarnings("unchecked")
  protected <T extends ViewModel> T resolveChildViewModel(@IdRes int id) {
    return (T) resolveFragment(id).viewModel();
  }

  protected VD binding() {
    return binding;
  }

  protected abstract void bind(VD binding);
}
