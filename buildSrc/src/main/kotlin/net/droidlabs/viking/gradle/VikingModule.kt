package net.droidlabs.viking.gradle

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.JavaVersion
import org.gradle.api.JavaVersion.VERSION_1_8
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.hasPlugin
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class VikingModule : Plugin<Project> {

  override fun apply(project: Project) {

    if (project.plugins.hasPlugin(LibraryPlugin::class) || project.plugins.hasPlugin(AppPlugin::class)) {

      with(project) {

        plugins.apply("org.jetbrains.kotlin.android")
        plugins.apply("org.jetbrains.kotlin.kapt")
        plugins.apply("org.jetbrains.kotlin.android.extensions")
        plugins.apply("de.mannodermaus.android-junit5")

        dependencies {
          listOf(
              Deps.junit5Api,
              Deps.junit5Params,
              Deps.junit5Kotlin,
              Deps.mockitoInline,
              Deps.mockitoCore,
              Deps.mockitoKotlin,
              Deps.truth,
              Deps.kotlinTest,
              Deps.kotlinReflect
          ).forEach {
            add("testImplementation", it)
          }

          listOf(
              Deps.junit5VintageEngine,
              Deps.junit5JupiterEngine
          ).forEach {
            add("testRuntimeOnly", it)
          }

          listOf(
              Deps.kotlinStdLib,
              Deps.kotlinCoroutines,
              Deps.kotlinCoroutinesAndroid,
              Deps.kotlinCoroutinesRx2,
              Deps.rxJava,
              Deps.rxKotlin,
              Deps.dagger,
              Deps.daggerAndroid,
              Deps.daggerAndroidSupport,
              Deps.timber,
              Deps.androidxAnnotation,

              //android-ktx
              Deps.androidxCoreKtx,
              Deps.androidxLifeCycle,
              Deps.androidxLifecycleExtensions,
              Deps.androidxLifecycleViewModelKtx,
              Deps.androidXLifecycleRuntime,
              Deps.androidxCollectionKtx,
              Deps.androidxActivityKtx,
              Deps.androidxFragmentKtx,
              Deps.androidxReactiveExtensionsKtx
          ).forEach {
            add("implementation", it)
          }

          listOf(
              Deps.daggerCompiler,
              Deps.daggerAndroidProccesor
          ).forEach {
            add("kapt", it)
          }
        }
      }

    }

    project.plugins.all {

      when (this) {

        is LibraryPlugin -> {
          val extension = project.extensions.getByType(LibraryExtension::class.java)
          extension.configureLibrary(project)
        }

        is AppPlugin -> {
          val extension = project.extensions.getByType(AppExtension::class.java)
          extension.configureApp(project)
        }

        is JavaPlugin -> {
          val extension = project.extensions.getByType(JavaPluginExtension::class.java)
          extension.configureJava(project)
        }
      }
    }

    project.tasks.withType<KotlinCompile> {
      kotlinOptions.jvmTarget = VERSION_1_8.toString()
    }

  }
}

private fun JavaPluginExtension.configureJava(project: Project) {
  sourceCompatibility = VERSION_1_8
  targetCompatibility = VERSION_1_8
}

private fun LibraryExtension.configureLibrary(project: Project) {
  setCompileSdkVersion(Versions.compileSdkVer)
  buildToolsVersion = Versions.buildToolsVer

  buildTypes {
    maybeCreate("release").apply {
      isMinifyEnabled = false
    }

    maybeCreate("debug").apply {
      isMinifyEnabled = false
    }
  }

  testOptions {
    unitTests.isReturnDefaultValues = true
  }

  dataBinding.apply {
    isEnabled = true
  }

  lintOptions {
    isAbortOnError = false
    //setDisable(mutableSetOf("LintBaseline"))
  }

  defaultConfig.apply {
    minSdkVersion(Versions.minSdkVer)
    targetSdkVersion(Versions.targetSdkVer)
    versionCode = 1
    versionName = "1.0"
    vectorDrawables.useSupportLibrary = true
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  compileOptions.apply {
    sourceCompatibility = VERSION_1_8
    targetCompatibility = VERSION_1_8
  }
}

private fun AppExtension.configureApp(project: Project) {
  setCompileSdkVersion(Versions.compileSdkVer)
  buildToolsVersion = Versions.buildToolsVer

  buildTypes {
    maybeCreate("release").apply {
      isMinifyEnabled = false
    }

    maybeCreate("debug").apply {
      isMinifyEnabled = false
    }
  }

  testOptions {
    unitTests.isReturnDefaultValues = true
  }

  dataBinding.apply {
    isEnabled = true
  }

  lintOptions {
    isAbortOnError = false
    setDisable(mutableSetOf("LintBaseline"))
  }

  defaultConfig.apply {
    minSdkVersion(Versions.minSdkVer)
    targetSdkVersion(Versions.targetSdkVer)
    versionCode = 1
    versionName = "1.0"
    vectorDrawables.useSupportLibrary = true
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  compileOptions.apply {
    sourceCompatibility = VERSION_1_8
    targetCompatibility = VERSION_1_8
  }
}