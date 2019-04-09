package net.droidlabs.viking.example.dialog.mvp;

import javax.inject.Inject;
import net.droidlabs.viking.mvp.Presenter;

public class VikingDialogPresenter extends Presenter<VikingDialogContract.View> implements VikingDialogContract.Presenter {
  @Inject
  public VikingDialogPresenter() { }
}
