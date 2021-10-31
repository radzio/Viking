package net.droidlabs.viking.gradle

import net.droidlabs.viking.gradle.Versions.androidXAnnotationVer
import net.droidlabs.viking.gradle.Versions.dagger2_version
import net.droidlabs.viking.gradle.Versions.jUnit5KotlinVer
import net.droidlabs.viking.gradle.Versions.jUnit5Ver
import net.droidlabs.viking.gradle.Versions.junitVer
import net.droidlabs.viking.gradle.Versions.kotlinCoroutinesVer
import net.droidlabs.viking.gradle.Versions.kotlin_version
import net.droidlabs.viking.gradle.Versions.mockitoVer
import net.droidlabs.viking.gradle.Versions.rxJavaVer
import net.droidlabs.viking.gradle.Versions.rxKotlinVer
import net.droidlabs.viking.gradle.Versions.timberVer
import net.droidlabs.viking.gradle.Versions.truthVer

object Versions {
  val kotlin_version = "1.4.21"
  val kotlinCoroutinesVer = "1.4.2"
  val androidGradlePluginVer = "4.1.1"

  val compileSdkVer = 29
  val targetSdkVer = 29
  val buildToolsVer = "28.0.3"
  val minSdkVer = 23

  val androidXAnnotationVer = "1.1.0"

  val timberVer = "4.7.1"
  val junitVer = "4.12"
  val mockitoVer = "2.27.0"
  val rxKotlinVer = "2.3.0"
  val rxJavaVer = "2.2.8"
  val dagger2_version = "2.27"
  val jUnit5Ver = "5.5.2"
  val jUnit5KotlinVer = "0.0.1"
  val truthVer = "1.0.1"
  val constraintLayoutVer = "1.1.3"

  val detektVersion = "1.2.2"
}

object Deps {
  //testImplementation
  val junit4 = "junit:junit:$junitVer"
  val junit5Api = "org.junit.jupiter:junit-jupiter-api:$jUnit5Ver"
  val junit5Params = "org.junit.jupiter:junit-jupiter-params:$jUnit5Ver"
  val junit5Kotlin = "de.jodamob.junit5:junit5-kotlin:$jUnit5KotlinVer"
  val mockitoInline = "org.mockito:mockito-inline:$mockitoVer"
  val mockitoCore = "org.mockito:mockito-core:$mockitoVer"
  val mockitoAndroid = "org.mockito:mockito-android:$mockitoVer"
  val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0"
  val truth = "com.google.truth:truth:$truthVer"
  val kotlinTest = "io.kotlintest:kotlintest-runner-junit5:3.3.2"

  //testRuntimeOnly
  val junit5VintageEngine = "org.junit.vintage:junit-vintage-engine:$jUnit5Ver"
  val junit5JupiterEngine = "org.junit.jupiter:junit-jupiter-engine:$jUnit5Ver"

  //androidTestImplementation
  val espressoCore = "androidx.test.espresso:espresso-core:3.3.0-alpha02"
  val espressoTestRules = "androidx.test:rules:1.3.0-alpha02"

  //implementation
  val android = "com.google.android:android:4.1.1.4"
  val androidxAnnotation = "androidx.annotation:annotation:$androidXAnnotationVer"

  val androidxCoreKtx = "androidx.core:core-ktx:1.2.0"
  val androidxCollectionKtx = "androidx.collection:collection-ktx:1.1.0"
  val androidxLifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
  val androidxLifeCycle = "androidx.lifecycle:lifecycle-common-java8:2.2.0"
  val androidXLifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:2.2.0"
  val androidxLifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
  val androidxActivityKtx = "androidx.activity:activity-ktx:1.2.0-rc01"
  val androidxFragmentKtx = "androidx.fragment:fragment-ktx:1.3.0-rc01"
  val androidxReactiveExtensionsKtx = "androidx.lifecycle:lifecycle-reactivestreams-ktx:2.2.0"

  val timber = "com.jakewharton.timber:timber:$timberVer"

  val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
  val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlin_version"
  val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVer"
  val kotlinCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlinCoroutinesVer"
  val kotlinCoroutinesRx2 = "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:$kotlinCoroutinesVer"

  val rxKotlin = "io.reactivex.rxjava2:rxkotlin:$rxKotlinVer"
  val rxJava = "io.reactivex.rxjava2:rxjava:$rxJavaVer"
  val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.1"

  val dagger = "com.google.dagger:dagger:$dagger2_version"
  val daggerCompiler = "com.google.dagger:dagger-compiler:$dagger2_version"
  val daggerAndroid = "com.google.dagger:dagger-android:$dagger2_version"
  val daggerAndroidSupport = "com.google.dagger:dagger-android-support:$dagger2_version"
  val daggerAndroidProccesor = "com.google.dagger:dagger-android-processor:$dagger2_version"

  val googlePlayServicesMaps = "com.google.android.gms:play-services-maps:17.0.0"
  val googleMapsUtils = "com.google.maps.android:android-maps-utils:0.5"

  val compiletesting = "com.google.testing.compile:compile-testing:0.18"
  val autoservice = "com.google.auto.service:auto-service:1.0-rc6"
  val autoserviceAnnotations = "com.google.auto.service:auto-service-annotations:1.0-rc6"
  val autocommon = "com.google.auto:auto-common:0.6"

  // Square
  val javapoet = "com.squareup:javapoet:1.7.0"

  // Google
  val guava = "com.google.guava:guava:25.1-jre"
}