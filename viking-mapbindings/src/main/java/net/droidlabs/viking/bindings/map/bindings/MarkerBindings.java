package net.droidlabs.viking.bindings.map.bindings;

import androidx.databinding.BindingAdapter;

import java.util.Collection;

import net.droidlabs.viking.bindings.map.listeners.ItemClickListener;
import net.droidlabs.viking.bindings.map.models.BindableMarker;
import net.droidlabs.viking.bindings.map.GoogleMapView;
import net.droidlabs.viking.bindings.map.InfoWindowAdapterFactory;
import net.droidlabs.viking.bindings.map.listeners.OnMarkerClickListener;
import net.droidlabs.viking.bindings.map.listeners.MarkerDragListener;

@SuppressWarnings({ "unused" })
public class MarkerBindings {
  private MarkerBindings() {
  }

  @BindingAdapter("gmv_markers")
  public static <T> void markers(GoogleMapView<T> googleMapView,
      Collection<BindableMarker<T>> markers) {
    if (markers == null) {
      return;
    }
    googleMapView.markers(markers);
  }

  @BindingAdapter("gmv_markerClickListener")
  public static <T> void markerClickListener(GoogleMapView<T> googleMapView,
      OnMarkerClickListener<BindableMarker<T>> markerClickListener) {
    googleMapView.setOnMarkerClickListener(markerClickListener);
  }

  @BindingAdapter("gmv_infoWindowAdapter")
  public static <T> void infoWindowAdapter(GoogleMapView<T> googleMapView,
      InfoWindowAdapterFactory<BindableMarker<T>> infoWindowAdapter) {
    googleMapView.setInfoWindowAdapter(infoWindowAdapter);
  }

  @BindingAdapter("gmv_infoWindowClickListener")
  public static <T> void infoWindowClick(GoogleMapView<T> googleMapView,
      ItemClickListener<BindableMarker<T>> itemClickListener) {
    googleMapView.setOnInfoWindowClickListener(itemClickListener);
  }

  @BindingAdapter("gmv_infoWindowCloseListener")
  public static <T> void onInfoWindowCloseListener(GoogleMapView<T> googleMapView,
      ItemClickListener<BindableMarker<T>> itemClickListener) {
    googleMapView.setOnInfoWindowCloseListener(itemClickListener);
  }

  @BindingAdapter("gmv_infoWindowLongClickListener")
  public static <T> void onInfoWindowLongClickListener(GoogleMapView<T> googleMapView,
      ItemClickListener<BindableMarker<T>> itemClickListener) {
    googleMapView.setOnInfoWindowLongClickListener(itemClickListener);
  }

  @BindingAdapter("gmv_markerDragListener")
  public static <T> void markerDragListener(GoogleMapView<T> googleMapView,
      MarkerDragListener<T> markerDragListener) {
    googleMapView.setMarkerDragListener(markerDragListener);
  }
}