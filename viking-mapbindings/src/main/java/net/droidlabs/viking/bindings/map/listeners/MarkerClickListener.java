package net.droidlabs.viking.bindings.map.listeners;

import com.google.android.gms.maps.model.Marker;

import net.droidlabs.viking.bindings.map.managers.MarkerManager;
import net.droidlabs.viking.bindings.map.models.BindableMarker;
import net.droidlabs.viking.bindings.map.adapters.MarkerClickAdapter;

public class MarkerClickListener<T> extends MarkerClickAdapter<BindableMarker<T>> {
  private final MarkerManager<T> markerManager;

  public MarkerClickListener(OnMarkerClickListener<BindableMarker<T>> markerClickListener,
      MarkerManager<T> markerManager) {
    super(markerClickListener);
    this.markerManager = markerManager;
  }

  @Override
  public BindableMarker<T> getModel(Marker marker) {
    return markerManager.retrieveBindableMarker(marker);
  }
}
