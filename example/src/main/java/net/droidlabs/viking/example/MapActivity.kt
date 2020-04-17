package net.droidlabs.viking.example

import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.gms.maps.MapView
import io.reactivex.Observable
import net.droidlabs.viking.annotations.AutoModule
import net.droidlabs.viking.annotations.AutoProvides
import net.droidlabs.viking.example.databinding.ActivityMapBinding
import net.droidlabs.viking.example.dialog.mvvm.DialogMvvmFragment
import net.droidlabs.viking.map.views.MapAwareActivityView
import net.droidlabs.viking.rx.util.AndroidCompositeDisposable
import net.droidlabs.viking.rx.util.AndroidDisposable

@AutoModule
class MapActivity : MapAwareActivityView<MapViewModel, ActivityMapBinding>(
    layoutIdRes = R.layout.activity_map,
    viewModelClass = MapViewModel::class.java
), AndroidDisposable by AndroidCompositeDisposable() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    lifecycle.addObserver(this)

    if (savedInstanceState == null) {
      supportFragmentManager
          .beginTransaction()
          .add(R.id.fragmentBla, MvvmFragment.newInstance())
          .commitAllowingStateLoss()
    }

    val dialogMvpButton = findViewById<Button>(R.id.button_dialog_mvp)
    dialogMvpButton.setOnClickListener { listener ->
      val fragmentManager = supportFragmentManager
      //val fragment = VikingDialogFragment.newInstance()
      //fragment.show(fragmentManager, DIALOG_FRAGMENT_TAG)
    }

    val dialogMvvmButton = findViewById<Button>(R.id.button_dialog_mvvm)
    dialogMvvmButton.setOnClickListener { listener ->
      val fragmentManager = supportFragmentManager
      val fragment = DialogMvvmFragment.newInstance()
      fragment.show(fragmentManager, DIALOG_FRAGMENT_TAG)
    }
  }

  override fun bind(binding: ActivityMapBinding) {
    binding.viewModel = viewModel()

//    viewModel().testSingleLiveEvent.observe(this, { integer ->
//      Log.d("test", String.format("%s", integer))
//      integer.navigate(Navigator(this@MapActivity))
//    })

    Observable.never<Int>()
        .doOnDispose {
          Log.d("TAG", "ON DISPOSE")
        }
        .subscribe()
        //.disposeAutomatically()
        .autoDispose()

  }

  override fun mapView(): MapView {
    return binding().map
  }

  @AutoProvides("test_string")
  fun test(): String {
    return "Hello world"
  }

  @AutoProvides("second_test_string")
  fun secondTest(): String {
    return "Goodbye cruel world"
  }

  companion object {
    private val DIALOG_FRAGMENT_TAG = "fragment_dialog"
  }
}