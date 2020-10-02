package net.droidlabs.viking.rx;

import net.droidlabs.viking.rx.util.BaseSchedulerProvider;
import net.droidlabs.viking.rx.util.SchedulerProvider;
import dagger.Module;
import dagger.Provides;

@Module
public class VikingRxModule {
  @Provides
  public BaseSchedulerProvider providesMainThreadShecduler() {
    return SchedulerProvider.getInstance();
  }
}
