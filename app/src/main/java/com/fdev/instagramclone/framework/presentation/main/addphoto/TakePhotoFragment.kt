package com.fdev.instagramclone.framework.presentation.main.addphoto

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.ImageView
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.fdev.instagramclone.R
import com.fdev.instagramclone.databinding.FragmentTakePhotoBinding
import com.fdev.instagramclone.framework.presentation.main.BaseMainFragment
import com.fdev.instagramclone.framework.presentation.main.MainActivity
import com.fdev.instagramclone.util.printLogD
import java.io.File
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class TakePhotoFragment : BaseMainFragment(R.layout.fragment_take_photo) {

    private val binding: FragmentTakePhotoBinding by viewBinding()

    private lateinit var cameraExecutor: ExecutorService

    companion object {
        const val REQUESTCODE = 1911
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        cameraExecutor =  Executors.newSingleThreadExecutor()
    }

    private fun initUI() {
        context?.let {context ->
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                activity?.let {
                    requestPermissions(it, arrayOf(Manifest.permission.CAMERA), REQUESTCODE)
                }
                //  initNoCameraPermission()
            } else {
                binding.apply {
                    cameraView.bindToLifecycle(viewLifecycleOwner)

                    innerTakephotoBtn.setOnClickListener {
                        val file = File(context.toString() + System.currentTimeMillis())
                        cameraView.takePicture(cameraExecutor , object : ImageCapture.OnImageCapturedCallback(){
                            override fun onCaptureSuccess(image: ImageProxy) {
                                printLogD("Success Taking image" , "format : ${image.format}")

                            }
                        })
                    }
                }
            }
        } ?: throw Exception("")


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


    private fun initNoCameraPermission() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        cameraExecutor.shutdown()
    }


}