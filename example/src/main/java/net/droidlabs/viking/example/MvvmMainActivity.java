package net.droidlabs.viking.example;

import net.droidlabs.viking.example.R;
import androidx.lifecycle.Observer;
import androidx.annotation.Nullable;
import android.util.Log;

import javax.inject.Inject;

import net.droidlabs.viking.annotations.AutoModule;
import net.droidlabs.viking.example.databinding.ActMvvmdemoBinding;
import net.droidlabs.viking.example.services.Navigator;
import net.droidlabs.viking.example.viewmodels.MainViewModel;
import net.droidlabs.viking.mvvm.ActivityView;

@AutoModule
public class MvvmMainActivity extends ActivityView<MainViewModel, ActMvvmdemoBinding> {

  public MvvmMainActivity() {
    super(R.layout.act_mvvmdemo, MainViewModel.class);
  }

  @Inject
  Navigator navigator;

  @Override
  protected void bind(ActMvvmdemoBinding binding) {

    viewModel().getTest().observe(this, new Observer<Integer>() {
      @Override
      public void onChanged(@Nullable Integer integer) {
        Log.d("test", String.format("%s", integer));
        navigator.openSecondActivity();
      }
    });

    binding.setViewModel(viewModel());
  }
}
