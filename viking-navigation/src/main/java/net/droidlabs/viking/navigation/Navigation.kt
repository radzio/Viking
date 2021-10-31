package net.droidlabs.viking.navigation

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RestrictTo
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.reflect.KClass

object Dispatcher {

  private val channel = MutableSharedFlow<Activity.() -> Unit>(replay = 0, extraBufferCapacity = 0)

  private val mutex = Mutex(locked = true)

  init {

    channel.subscriptionCount
        .map { count -> count > 0 } // map count into active/inactive flag
        .distinctUntilChanged() // only react to true<->false changes
        .onEach {
          if (it) {
            mutex.unlock()
          } else {
            mutex.lock()
          }
        }
        .launchIn(GlobalScope) // launch it
  }

  suspend fun sendEvent(event: Activity.() -> Unit) {
    mutex.withLock {}
    channel.emit(event)
  }

  suspend fun requestPermission(permission: String): Boolean {
    return getPermission(permission)
  }

  suspend inline fun <reified T : Activity> getResult(
      crossinline block: Intent.(Activity) -> Unit = {}
  ): ActivityResult {
    val deferred = CompletableDeferred<ActivityResult>()

    sendEvent {
      val fragment = ActivityResultFragment()
      (this as? FragmentActivity)?.supportFragmentManager
          ?.beginTransaction()
          ?.add(fragment, "aaa")
          ?.commitNowAllowingStateLoss();

      val intent = Intent(this, T::class.java)

      block(intent, this)

      fragment.onResult(intent, deferred)
    }

    return deferred.await()
  }

  suspend fun getPermission(permission: String): Boolean {
    val deferred = CompletableDeferred<Boolean>()

    sendEvent {
      val fragment = ActivityResultFragment()
      (this as? FragmentActivity)?.supportFragmentManager
          ?.beginTransaction()
          ?.add(fragment, "aaabb")
          ?.commitNowAllowingStateLoss();

      fragment.getPermission(permission, deferred)
    }

    return deferred.await()
  }

  val events: Flow<Activity.() -> Unit> = channel.asSharedFlow()
}

class Navigation : Application.ActivityLifecycleCallbacks {

  private val jobs: MutableMap<KClass<out Any>, Job> = mutableMapOf()

  override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

  override fun onActivityStarted(activity: Activity) {}

  override fun onActivityResumed(activity: Activity) {

    jobs[activity::class] = (activity as AppCompatActivity).lifecycleScope.launch {

      Dispatcher.events.collect {
        //delay(5000L)
        Log.d("TEST", "${activity.javaClass.simpleName}")
        it(activity)
      }
    }
  }

  override fun onActivityPaused(activity: Activity) {
    jobs[activity::class]?.cancel()
  }

  override fun onActivityStopped(activity: Activity) {}
  override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
  override fun onActivityDestroyed(activity: Activity) {}
}

@RestrictTo(RestrictTo.Scope.LIBRARY)
class ActivityResultFragment : Fragment() {

  private val viewModel by viewModels<ActivityResultFragmentViewModel>()

  private val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
    viewModel.setResult(it)
  }

  private val permissionResult = registerForActivityResult(ActivityResultContracts.RequestPermission()) { hasPermission ->
    viewModel.setPermissionResult(hasPermission)
  }

  init {
    lifecycleScope.launchWhenCreated {
      viewModel.removeFragment.collect {
        removeFragment()
      }
    }
  }

  fun onResult(intent: Intent, deferred: CompletableDeferred<ActivityResult>) {
    viewModel.result = deferred
    activityResult.launch(intent)
  }

  fun getPermission(permission: String, deferred: CompletableDeferred<Boolean>) {
    viewModel.permissionResult = deferred
    permissionResult.launch(permission)
  }

  private fun removeFragment() {
    requireActivity()
        .supportFragmentManager
        .beginTransaction()
        .remove(this)
        .commitAllowingStateLoss()
  }

  class ActivityResultFragmentViewModel : ViewModel() {
    var result: CompletableDeferred<ActivityResult>? = null
    var permissionResult: CompletableDeferred<Boolean>? = null
    private val _removeFragment: MutableSharedFlow<Unit> = MutableSharedFlow()
    val removeFragment = _removeFragment.asSharedFlow()

    fun setResult(activityResult: ActivityResult) {
      result?.complete(activityResult)
      viewModelScope.launch {
        _removeFragment.emit(Unit)
      }
    }

    fun setPermissionResult(hasPermission: Boolean) {

      Log.d("TEST", "hasPermission: $hasPermission")
      permissionResult?.complete(hasPermission)
      viewModelScope.launch {
        _removeFragment.emit(Unit)
      }
    }

    override fun onCleared() {
      super.onCleared()
      Log.d("TEST", "On cleared!")
    }
  }

}