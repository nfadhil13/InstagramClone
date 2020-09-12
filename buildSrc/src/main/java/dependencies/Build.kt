package dependencies

object Build {

    val build_tools = "com.android.tools.build:gradle:${Versions.gradle}"
    val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    val hilt_build = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    val google_services = "com.google.gms:google-services:${Versions.google_services_version}"
    val firebase_crashlytics_gradle = "com.google.firebase:firebase-crashlytics-gradle:${Versions.firebase_crashlyticsGradle_version}"

}