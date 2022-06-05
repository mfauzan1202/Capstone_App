package com.company.capstoneapp.ui.camera

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.company.capstoneapp.databinding.ActivityResultCameraBinding
import com.company.capstoneapp.rotateBitmap
import java.io.File


class ResultCameraActivity : AppCompatActivity() {
    companion object{
        const val CAMERA_X_RESULT = 200
    }
    private lateinit var binding: ActivityResultCameraBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
            val intent = Intent(this@ResultCameraActivity, CameraActivity::class.java)
            launcherIntentCameraX.launch(intent)

        binding.ivBack.setOnClickListener{
            launcherIntentCameraX.launch(Intent(this@ResultCameraActivity, CameraActivity::class.java))
        }
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )

            binding.ivPreviewImage.setImageBitmap(result)
        }
    }


}