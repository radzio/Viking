package net.droidlabs.viking.example.dialog.mvp;

import net.droidlabs.viking.annotations.AutoModule;
import net.droidlabs.viking.example.R;
import net.droidlabs.viking.mvp.DialogFragmentScreen;
import androidx.fragment.app.DialogFragment;

@AutoModule
public class VikingDialogFragment extends DialogFragmentScreen<VikingDialogContract.View, VikingDialogPresenter>
    implements VikingDialogContract.View {

  public static VikingDialogFragment newInstance() {
    VikingDialogFragment fragment = new VikingDialogFragment();
    fragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    return fragment;
  }

  public VikingDialogFragment() {
    super(R.layout.fragment_viking_dialog, VikingDialogPresenter.class);
  }
}