package net.droidlabs.viking.example

import android.util.Log
import net.droidlabs.viking.mvvm.ViewModel
import javax.inject.Inject

class TestViewModel2 @Inject constructor() : ViewModel() {
  init {
    Log.d("Test", "Hello!")
  }
}