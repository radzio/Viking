package net.droidlabs.viking.annotations;

import dagger.MapKey;

@MapKey
public @interface ScreenKey {
  Class<?> value();
}
