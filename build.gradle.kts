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
      classpath( "com.android.tools.build:gradle:4.1.1")
      //classpath( "com.novoda:bintray-release:0.9.2")

      classpath( "com.github.marandaneto:bintray-release:3bde108072")
      classpath( "org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.21")
      classpath ("de.mannodermaus.gradle.plugins:android-junit5:1.7.0.0")
      //classpath( "org.jetbrains.dokka:dokka-android-gradle-plugin:1.4.20")
      classpath( "org.jetbrains.dokka:dokka-gradle-plugin:1.4.20")
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
buildscript {

  repositories {
    google()
    jcenter()
    mavenCentral()
    maven ( url  = "https://plugins.gradle.org/m2/" )
    maven ( url = "https://jitpack.io" )
  }

  val kotlin_version by extra("1.4.21")
  dependencies {
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
  }
}
