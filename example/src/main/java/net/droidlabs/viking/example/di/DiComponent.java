package net.droidlabs.viking.example.di;

import android.app.Application;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;
import net.droidlabs.dagger.annotations.AppScope;

@AppScope
@Component(modules = {
    AndroidInjectionModule.class,
    AndroidSupportInjectionModule.class,
    //ScreenMappings.class,
    AppModule.class,
    TestModule.class,
})
public interface DiComponent extends AndroidInjector<DaggerApplication> {

  @Override
  void inject(DaggerApplication instance);

  @Component.Builder
  interface Builder {

    @BindsInstance
    DiComponent.Builder application(Application application);

    DiComponent build();
  }
}
