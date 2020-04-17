package net.droidlabs.viking.example.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class Test @Inject constructor() : ViewModel() {
  override fun onCleared() {
    super.onCleared()
    Log.d("AA", "Cleared")
  }
}