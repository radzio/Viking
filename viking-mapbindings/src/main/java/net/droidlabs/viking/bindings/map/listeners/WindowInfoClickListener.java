package net.droidlabs.viking.bindings.map.listeners;

import com.google.android.gms.maps.model.Marker;

import net.droidlabs.viking.bindings.map.managers.MarkerManager;
import net.droidlabs.viking.bindings.map.models.BindableMarker;
import net.droidlabs.viking.bindings.map.adapters.InfoWindowClickAdapter;

public class WindowInfoClickListener<T> extends InfoWindowClickAdapter<BindableMarker<T>> {
  private final MarkerManager<T> markerManager;

  public WindowInfoClickListener(ItemClickListener<BindableMarker<T>> itemClickListener,
      MarkerManager<T> markerManager) {
    super(itemClickListener);
    this.markerManager = markerManager;
  }

  @Override
  public BindableMarker<T> getModel(Marker marker) {
    return markerManager.retrieveBindableMarker(marker);
  }
}