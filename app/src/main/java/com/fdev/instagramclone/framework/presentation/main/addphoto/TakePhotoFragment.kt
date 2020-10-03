package com.fdev.instagramclone.framework.presentation.main.addphoto

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.hardware.camera2.CameraMetadata.LENS_FACING_BACK
import android.hardware.camera2.CameraMetadata.LENS_FACING_FRONT
import android.os.Bundle
import android.view.View
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.FlashMode
import androidx.camera.core.ImageProxy
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import by.kirich1409.viewbindingdelegate.viewBinding
import com.fdev.instagramclone.R
import com.fdev.instagramclone.databinding.FragmentTakePhotoBinding
import com.fdev.instagramclone.framework.presentation.main.BaseMainFragment
import com.fdev.instagramclone.framework.presentation.toBitmap
import com.fdev.instagramclone.util.printLogD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class TakePhotoFragment : BaseMainFragment(R.layout.fragment_take_photo) {

    private val binding: FragmentTakePhotoBinding by viewBinding()

    private lateinit var cameraExecutor: ExecutorService

    private var onImageSelected : OnImageSelected? = null

    private var convertJob : Job? = null

    companion object {
        const val REQUESTCODE = 1911
        @FlashMode private const val FLASH_MODE_ON = 2
        @FlashMode private const val FLASH_MODE_OFF = 4
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        cameraExecutor =  Executors.newSingleThreadExecutor()
    }

    private fun initUI() {
        context?.let { context ->
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                activity?.let {
                    requestPermissions(it, arrayOf(Manifest.permission.CAMERA), REQUESTCODE)
                }
                //  initNoCameraPermission()
            } else {
                binding.apply {
                    cameraView.bindToLifecycle(viewLifecycleOwner)
                    innerCircleBtn.setOnClickListener {
                        cameraView.takePicture(cameraExecutor, object : ImageCapture.OnImageCapturedCallback() {
                            override fun onCaptureSuccess(image: ImageProxy) {
                                printLogD("Success Taking image", "format : ${image.format}")
                                processImage(image)
                            }
                        })
                    }
                    rotateCamera.setOnClickListener {
                        if(cameraView.cameraLensFacing == LENS_FACING_FRONT){
                            cameraView.cameraLensFacing = LENS_FACING_BACK
                        }else{
                            cameraView.cameraLensFacing = LENS_FACING_FRONT
                        }
                    }

                    flashCamera.setOnClickListener {
                        if(cameraView.flash == ImageCapture.FLASH_MODE_ON){
                            printLogD("TakePhotoFragment", "FLASH MODE OFF")
                            cameraView.flash = ImageCapture.FLASH_MODE_OFF
                            flashCamera.setImageResource(R.drawable.ic_baseline_flash_on_24)
                        }else{
                            printLogD("TakePhotoFragment", "FLASH_MODE_ON")
                            cameraView.flash == ImageCapture.FLASH_MODE_ON
                            flashCamera.setImageResource(R.drawable.ic_baseline_flash_off_24)
                        }
                    }
                }
        }
        } ?: throw Exception("")



    }


    fun initLoading() {

    }

    @SuppressLint("UnsafeExperimentalUsageError")
    fun processImage(imageProxy: ImageProxy){
        convertJob = CoroutineScope(Default).launch{
            val imageBitmap = imageProxy.image?.toBitmap(300, 300)
            imageProxy.close()
            imageBitmap?.let{
                onImageSelected?.onImageSelected(it)
            }?: onImageSelected?.onError()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {

            REQUESTCODE -> {
                if ((grantResults.isNotEmpty() &&
                                grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    initUI()
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return
            }

        }
    }

    fun setOnImageSelected(onImageSelected: OnImageSelected){
        this.onImageSelected = onImageSelected
    }

    private fun initNoCameraPermission() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        cameraExecutor.shutdown()
        convertJob?.let{
            if(it.isActive){
                it.cancel()
            }
        }
    }



}