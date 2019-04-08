package agency.tango.viking.example;

import agency.tango.viking.example.di.DaggerDiComponent;
import android.content.Context;
import androidx.multidex.MultiDex;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

//import agency.tango.viking.example.di.DaggerDiComponent;

public class App extends DaggerApplication {

  @Override
  public void onCreate() {
    super.onCreate();
    //if (LeakCanary.isInAnalyzerProcess(this)) {
    //  // This process is dedicated to LeakCanary for heap analysis.
    //  // You should not init your app in this process.
    //  return;
    //}
    //LeakCanary.install(this);
  }

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }

  @Override
  protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
    return DaggerDiComponent.builder().application(this).build();
  }
}
