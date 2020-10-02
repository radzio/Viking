package net.droidlabs.viking.map.views

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import net.droidlabs.viking.mvvm.ActivityView
import net.droidlabs.viking.mvvm.ViewModel

abstract class MapAwareActivityView<VM : ViewModel, VD : ViewDataBinding>(
    @LayoutRes layoutIdRes: Int,
    viewModelClass: Class<VM>
) : ActivityView<VM, VD>(layoutIdRes, viewModelClass) {

  override fun onCreate(savedInstanceState: Bundle?) {
    MapsInitializer.initialize(this)
    super.onCreate(savedInstanceState)
    val mapViewBundle: Bundle?
    if (savedInstanceState != null) {
      mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY)
    } else {
      mapViewBundle = Bundle()
    }
    mapView().onCreate(mapViewBundle)
  }

  abstract fun mapView(): MapView

  public override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    var mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY)
    if (mapViewBundle == null) {
      mapViewBundle = Bundle()
      outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle)
    }

    mapView().onSaveInstanceState(mapViewBundle)
  }

  override fun onResume() {
    super.onResume()
    mapView().onResume()
  }

  public override fun onStart() {
    super.onStart()
    mapView().onStart()
  }

  public override fun onStop() {
    super.onStop()
    mapView().onStop()
  }

  override fun onPause() {
    mapView().onPause()
    super.onPause()
  }

  public override fun onDestroy() {
    mapView().onDestroy()
    binding().unbind() // TODO Remove when all bindings will be memory leak safe
    super.onDestroy()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView().onLowMemory()
  }

  companion object {

    private val MAPVIEW_BUNDLE_KEY = "mapview_bundle_key"
  }
}
