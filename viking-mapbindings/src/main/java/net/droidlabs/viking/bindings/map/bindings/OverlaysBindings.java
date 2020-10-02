package net.droidlabs.viking.bindings.map.bindings;

import androidx.databinding.BindingAdapter;

import java.util.Collection;

import net.droidlabs.viking.bindings.map.listeners.ItemClickListener;
import net.droidlabs.viking.bindings.map.GoogleMapView;
import net.droidlabs.viking.bindings.map.models.BindableOverlay;

@SuppressWarnings({ "unused" })
public class OverlaysBindings {
  private OverlaysBindings() {
  }

  @BindingAdapter("gmv_groundOverlays")
  public static void setGroundOverlays(GoogleMapView googleMapView,
      Collection<? extends BindableOverlay> groundOverlay) {
    if (groundOverlay == null) {
      return;
    }

    googleMapView.groundOverlays(groundOverlay);
  }

  @BindingAdapter("gmv_groundOverlayClickListener")
  public static void onGroundOverlayClickListener(GoogleMapView googleMapView,
      ItemClickListener<BindableOverlay> itemClickListener) {
    googleMapView.setOnGroundOverlayClickListener(itemClickListener);
  }
}