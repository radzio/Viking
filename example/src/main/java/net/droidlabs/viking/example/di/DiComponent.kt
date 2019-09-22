package net.droidlabs.viking.example.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import net.droidlabs.dagger.annotations.AppScope

@AppScope
@Component(modules = [
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    net.droidlabs.viking.di.ScreenMappings::class,
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
