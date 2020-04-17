package net.droidlabs.viking.example.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import net.droidlabs.dagger.annotations.AppScope

@Module
class AppModule {
  @Provides
  @AppScope
  fun provideContext(application: Application): Context {
    return application
  }
}