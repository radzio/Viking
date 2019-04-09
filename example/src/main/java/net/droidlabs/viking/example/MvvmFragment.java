package net.droidlabs.viking.example;

import net.tango.viking.annotations.AutoModule;
import net.droidlabs.viking.example.R;
import net.droidlabs.viking.example.databinding.FragmentTestBinding;
import net.droidlabs.viking.mvvm.FragmentView;

@AutoModule(scopes = {MapActivity.class})
public class MvvmFragment extends FragmentView<TestViewModel2, FragmentTestBinding> {

  public static MvvmFragment newInstance() {
    return new MvvmFragment();
  }

  public MvvmFragment() {
    super(R.layout.fragment_test, TestViewModel2.class);
  }

  @Override
  protected void bind(FragmentTestBinding binding) {

  }
}
