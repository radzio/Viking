package net.droidlabs.viking.bindings.map;

import android.content.Context;

import net.droidlabs.viking.bindings.map.adapters.CustomInfoWindowAdapter;

public interface InfoWindowAdapterFactory<T> {
  CustomInfoWindowAdapter<T> createInfoWindowAdapter(Context context);
}
