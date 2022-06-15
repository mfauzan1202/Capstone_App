package com.company.capstoneapp.activity.camera

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.company.capstoneapp.*
import com.company.capstoneapp.databinding.ActivityResultCameraBinding
import com.company.capstoneapp.activity.detail.DetailActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.File


class ResultCameraActivity : AppCompatActivity() {

    private var getFile: File? = null
    private lateinit var binding: ActivityResultCameraBinding
    private lateinit var food: String
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this@ResultCameraActivity, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)

        database = Firebase.database.reference.child("makanan")

        binding.apply {

            val toolbar = toolbar
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_ios_white_24)

            btnScan.setOnClickListener {
                val detailPage = Intent(this@ResultCameraActivity, DetailActivity::class.java)
                detailPage.putExtra(DetailActivity.EXTRA_ID, food)
                startActivity(detailPage)
            }
        }
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            food = it.data?.getStringExtra("food") as String

            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )

            val file = reduceFileImage(result, myFile)

            getFile = file
            binding.ivPreviewImage.setImageBitmap(result)

            database.child(food).get().addOnSuccessListener {
                binding.tvFoodName.text = it.child("name").value.toString()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId === android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }

    companion object{
        const val CAMERA_X_RESULT = 200
    }
}