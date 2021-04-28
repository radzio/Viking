// Top-level build file where you can add configuration options common to all sub-projects/modules.


subprojects {

  repositories {
    google()
    mavenCentral()
    maven ( url  = "https://plugins.gradle.org/m2/" )
    maven ( url = "https://jitpack.io" )
  }

  buildscript {
    repositories {
      google()
      jcenter()
      mavenCentral()
      maven ( url  = "https://plugins.gradle.org/m2/" )
      maven ( url = "https://jitpack.io" )
    }
    dependencies {
      classpath( "com.android.tools.build:gradle:3.6.1")
      //classpath( "com.novoda:bintray-release:0.9.2")

      classpath( "com.github.marandaneto:bintray-release:3bde108072")
      classpath( "org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.0")
      classpath ("de.mannodermaus.gradle.plugins:android-junit5:1.5.1.0")
      classpath( "org.jetbrains.dokka:dokka-android-gradle-plugin:0.9.18")
      classpath( "org.jetbrains.dokka:dokka-gradle-plugin:0.9.18")
      classpath( "me.tatarka:gradle-retrolambda:3.7.0")
      //classpath( "com.dicedmelon.gradle:jacoco-android:0.1.4")
      classpath( "com.github.arturdm:jacoco-android-gradle-plugin:dd9d8ceaa9")
    }
  }
}

allprojects {
  repositories {
    google()
    jcenter()
    mavenCentral()
    maven ( url  = "https://plugins.gradle.org/m2/" )
    maven ( url = "https://jitpack.io" )
  }
}