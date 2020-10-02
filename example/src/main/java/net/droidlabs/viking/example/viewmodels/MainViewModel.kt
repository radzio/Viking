package net.droidlabs.viking.example.viewmodels

import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import net.droidlabs.viking.example.BR
import net.droidlabs.viking.example.SingleLiveEvent
import net.droidlabs.viking.mvvm.ViewModel
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
          Log.d("YOLO", test.toString())
          yolo = test.toString()
        })
  }

  override fun start() = ssij {
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
  }

  fun click() {
    Log.d("test", "Click")
    test.postValue(value)
  }

}
