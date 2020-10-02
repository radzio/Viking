package net.droidlabs.viking.bindings.map.bindings;

import androidx.databinding.BindingAdapter;

import java.util.Collection;

import net.droidlabs.viking.bindings.map.listeners.ItemClickListener;
import net.droidlabs.viking.bindings.map.models.BindablePolygon;
import net.droidlabs.viking.bindings.map.GoogleMapView;

@SuppressWarnings({ "unused" })
public class PolygonBindings {
  private PolygonBindings() {
  }

  @BindingAdapter("gmv_polygons")
  public static void setPolygons(GoogleMapView googleMapView,
      Collection<BindablePolygon> polygons) {
    if (polygons == null) {
      return;
    }
    googleMapView.polygons(polygons);
  }

  @BindingAdapter("gmv_polygonClickListener")
  public static void polygonClickListener(GoogleMapView googleMapView,
      ItemClickListener<? extends BindablePolygon> itemClickListener) {
    googleMapView.setOnPolygonClickListener(itemClickListener);
  }
}
