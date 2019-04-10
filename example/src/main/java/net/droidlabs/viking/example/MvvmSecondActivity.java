package net.droidlabs.viking.example;

import net.droidlabs.viking.annotations.AutoModule;
import net.droidlabs.viking.example.R;
import net.droidlabs.viking.example.databinding.ActMvvmdemoBinding;
import net.droidlabs.viking.example.viewmodels.SecondViewModel;
import net.droidlabs.viking.mvvm.ActivityView;

@AutoModule
public class MvvmSecondActivity extends ActivityView<SecondViewModel, ActMvvmdemoBinding> {

  public MvvmSecondActivity() {
    super(R.layout.act_mvvmdemo, SecondViewModel.class);
  }

  @Override
  protected void bind(ActMvvmdemoBinding binding) {

  }
}
