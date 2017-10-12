package agency.tango.viking.mvvm;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;

import java.io.Serializable;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class ActivityView<VM extends ViewModel, VD extends ViewDataBinding> extends
    DaggerAppCompatActivity {

  private VM viewModel;

  @Inject
  ViewModelProvider.Factory viewModelFactory;

  private VD binding;
  private int layoutIdRes;
  private final Class<VM> viewModelClass;
  private ViewModelDelegate<VM> viewModelDelegate;

  public ActivityView(@LayoutRes int layoutIdRes, Class<VM> viewModelClass) {
    this.layoutIdRes = layoutIdRes;
    this.viewModelClass = viewModelClass;
  }

  public final VM viewModel() {
    return viewModel;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass);
    viewModelDelegate = new ViewModelDelegate<>(viewModel);
    binding = DataBindingUtil.setContentView(this, layoutIdRes);
    bind(binding);

    viewModelDelegate.onCreate(savedInstanceState);
  }

  @Override
  protected void onStart() {
    super.onStart();
    viewModelDelegate.onStart();
  }

  @Override
  protected void onStop() {
    viewModelDelegate.onStop();
    super.onStop();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    viewModelDelegate.onActivityResult(requestCode, resultCode, data);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    viewModelDelegate.onSaveInstanceState(outState);
  }

  @SuppressWarnings("unchecked")
  protected <T extends FragmentView> T resolveFragment(@IdRes int id) {
    return (T) getSupportFragmentManager().findFragmentById(id);
  }

  @SuppressWarnings("unchecked")
  protected <T extends ViewModel> T resolveChildViewModel(@IdRes int id) {
    return (T) resolveFragment(id).viewModel();
  }

  protected abstract void bind(VD binding);

  protected VD binding() {
    return binding;
  }

  @SuppressWarnings("unchecked")
  protected <T extends Serializable> T getIntentExtra(String key) {
    return (T) getIntent().getSerializableExtra(key);
  }
}
