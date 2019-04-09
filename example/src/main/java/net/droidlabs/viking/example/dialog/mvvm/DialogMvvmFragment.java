package net.droidlabs.viking.example.dialog.mvvm;

import net.tango.viking.annotations.AutoModule;
import net.droidlabs.viking.example.R;
import net.droidlabs.viking.example.TestViewModel2;
import net.droidlabs.viking.example.databinding.FragmentVikingDialogBinding;
import net.droidlabs.viking.mvvm.DialogFragmentView;

@AutoModule
public class DialogMvvmFragment extends DialogFragmentView<TestViewModel2, FragmentVikingDialogBinding> {
  public static DialogMvvmFragment newInstance() {
    return new DialogMvvmFragment();
  }

  public DialogMvvmFragment() {
    super(R.layout.fragment_viking_dialog, TestViewModel2.class);
  }

  @Override
  protected void bind(FragmentVikingDialogBinding binding) {

  }
}
