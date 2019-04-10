package net.droidlabs.viking.rx;

import net.droidlabs.viking.mvvm.ViewModel;
import net.droidlabs.viking.rx.util.BaseSchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;

public class RxViewModel extends ViewModel {

  private final BaseSchedulerProvider schedulerProvider;
  protected final CompositeDisposable disposables;

  public RxViewModel(BaseSchedulerProvider schedulerProvider) {
    this.schedulerProvider = schedulerProvider;
    this.disposables = new CompositeDisposable();
  }

  public BaseSchedulerProvider schedulerProvider() {
    return schedulerProvider;
  }

  @Override
  public void stop() {
    super.stop();
    disposables.clear();
  }
}