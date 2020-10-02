package net.droidlabs.viking.example.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import javax.inject.Inject;

import net.droidlabs.viking.annotations.ProvidesViewModel;
import net.droidlabs.viking.example.SingleLiveEvent;
import net.droidlabs.viking.example.services.Navigator;
import net.droidlabs.viking.mvvm.ViewModel;

@ProvidesViewModel
public class MainViewModel extends ViewModel {
  private Navigator navigator;

  MutableLiveData<Integer> test = new SingleLiveEvent<>();
  private int value = 0;

  public LiveData<Integer> getTest() {
    return test;
  }

  @Inject
  public MainViewModel() {

  }

  public void click() {
    Log.d("test", "Click");
    test.postValue(value);
  }

}
