package net.droidlabs.viking.example.viewmodels

import android.Manifest
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import net.droidlabs.viking.example.BR
import net.droidlabs.viking.example.MapActivity
import net.droidlabs.viking.example.MvvmSecondActivity
import net.droidlabs.viking.example.SingleLiveEvent
import net.droidlabs.viking.mvvm.ViewModel
import net.droidlabs.viking.navigation.Dispatcher
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {
  internal var test: MutableLiveData<Int> = SingleLiveEvent()
  private val value = 0

  fun getTest(): LiveData<Int> {
    return test
  }

  @get:Bindable
  var yolo: String by observable("0", BR.yolo)

  private val disposable = CompositeDisposable()

  init {
    disposable.add(Observable.interval(1, TimeUnit.SECONDS, Schedulers.io())
        .subscribe { test ->
         // Log.d("YOLO", test.toString())
          yolo = test.toString()
        })
  }

  override fun start() {
    super.start()
    Log.d("TAG", "vm-START")
  }

  override fun stop() {
    super.stop()
    Log.d("TAG", "vm-STOP")
  }

  override fun onCleared() {
    super.onCleared()
    disposable.clear()
    Log.d("TEST", "vm-ONCLEAER")
  }

  fun openActivity() {

    viewModelScope.launch {
      Dispatcher.sendEvent {
        startActivity(Intent(this, MapActivity::class.java))
      }
    }
  }

  fun requestPermission() {
    viewModelScope.launch {
      val result = Dispatcher.requestPermission(Manifest.permission.ACCESS_FINE_LOCATION)
      Log.d("TEST", "outside Result for ${Manifest.permission.ACCESS_FINE_LOCATION} is $result")
      Dispatcher.sendEvent {
        Log.d("TEST", " inside Result for ${Manifest.permission.ACCESS_FINE_LOCATION} is $result")
        Toast.makeText(this, "Result for ${Manifest.permission.ACCESS_FINE_LOCATION} is $result", Toast.LENGTH_LONG).show()
      }

    }
  }



  fun openForResult() {
    viewModelScope.launch {
      val result = Dispatcher.getResult<MvvmSecondActivity> { activity ->
        this.putExtra("bla", "${activity.callingPackage}")
      }
      Log.d("TEST", "outside Result for getResult is $result")
//      Dispatcher.get {
//        Log.d("TEST", " inside Result for ${Manifest.permission.ACCESS_FINE_LOCATION} is $result")
//        Toast.makeText(this, "Result for ${Manifest.permission.ACCESS_FINE_LOCATION} is $result", Toast.LENGTH_LONG).show()
//      }

    }
  }

}
