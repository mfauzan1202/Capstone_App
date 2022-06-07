package com.company.capstoneapp.ui.camera

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.company.capstoneapp.*
import com.company.capstoneapp.databinding.ActivityResultCameraBinding
import com.company.capstoneapp.ui.DetailActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class ResultCameraActivity : AppCompatActivity() {
    companion object{
        const val CAMERA_X_RESULT = 200
    }
    private var getFile: File? = null
    private lateinit var binding: ActivityResultCameraBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
            val intent = Intent(this@ResultCameraActivity, CameraActivity::class.java)
            launcherIntentCameraX.launch(intent)

        binding.apply {
            ivBack.setOnClickListener{
                launcherIntentCameraX.launch(Intent(this@ResultCameraActivity, CameraActivity::class.java))
            }

            btnScan.setOnClickListener {
                uploadImage()
            }
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
            val file = reduceFileImage(result, myFile)
            getFile = file
            binding.ivPreviewImage.setImageBitmap(result)
        }
    }

    private fun uploadImage() {
        showLoading(true, this@ResultCameraActivity)
        if (getFile != null) {

            val file = getFile as File
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file",
                file.name,
                requestImageFile
            )

            ApiConfig.getApiService("http://10.10.200.219:8080/")
                .uploadImage(imageMultipart)
                .enqueue(object : Callback<DetectionResponse> {
                    override fun onResponse(
                        call: Call<DetectionResponse>,
                        response: Response<DetectionResponse>
                    ) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody != null) {
                                showLoading(false, this@ResultCameraActivity)
                                Toast.makeText(
                                    this@ResultCameraActivity,
                                    responseBody.makanan,
                                    Toast.LENGTH_SHORT
                                ).show()
                                val detailPage = Intent(this@ResultCameraActivity, DetailActivity::class.java)
                                detailPage.putExtra(DetailActivity.EXTRA_ID, responseBody.makanan)
                                startActivity(detailPage)
                            }
                        } else {
                            Toast.makeText(
                                this@ResultCameraActivity,
                                response.message(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<DetectionResponse>, t: Throwable) {
                        Toast.makeText(
                            this@ResultCameraActivity,
                            "Gagal instance Retrofit",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })
        } else {
            Toast.makeText(
                this,
                "Silakan masukkan berkas gambar terlebih dahulu.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}