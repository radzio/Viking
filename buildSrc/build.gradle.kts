buildscript {

    repositories {
        jcenter()
    }

    dependencies {

    }
}

repositories {
    google()
    mavenCentral()
    jcenter()
}

plugins {
    `kotlin-dsl`
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

gradlePlugin {
    plugins {
        register("VikingModule") {
            id = "VikingModule"
            implementationClass = "net.droidlabs.viking.gradle.VikingModule"
        }
    }
}

dependencies {
    implementation(gradleApi())

    implementation("com.android.tools.build:gradle:4.1.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.21")
}