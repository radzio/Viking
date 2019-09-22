package net.droidlabs.viking.example.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import net.droidlabs.dagger.annotations.AppScope
import net.droidlabs.viking.di.ScreenMappings

@AppScope
@Component(modules = [
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    ScreenMappings::class,
    AppModule::class,
    TestModule::class]
)
interface DiComponent : AndroidInjector<DaggerApplication> {

    override fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): DiComponent.Builder

        fun build(): DiComponent
    }
}
