package dependencies

object AnnotationProcess {

    val roomAnnotationProcess = "androidx.room:room-compiler:${Versions.room_version}"
    val lifeCycleAnnotationProcess = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycleVersion}"
    val hiltAnnotationProcess = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    val hiltViewModelAnnotationProcess = "androidx.hilt:hilt-compiler:${Versions.hilt_viewmodel}"
}