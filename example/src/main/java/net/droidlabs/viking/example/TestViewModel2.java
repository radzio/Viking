package net.droidlabs.viking.example;

import android.util.Log;

import javax.inject.Inject;

import net.droidlabs.viking.annotations.ProvidesViewModel;
import net.droidlabs.viking.mvvm.ViewModel;

@ProvidesViewModel
  public  class TestViewModel2 extends ViewModel {

    @Inject
    public TestViewModel2() {
      Log.d("Test", "Hello!");
    }
  }