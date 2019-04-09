package net.droidlabs.viking.example.viewmodels;

import javax.inject.Inject;

import net.tango.viking.annotations.ProvidesViewModel;
import net.droidlabs.viking.mvvm.ViewModel;

@ProvidesViewModel
public class SecondViewModel extends ViewModel {
  @Inject
  public SecondViewModel() {
  }
}
