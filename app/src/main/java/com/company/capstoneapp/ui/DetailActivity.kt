package com.company.capstoneapp.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.company.capstoneapp.R
import com.company.capstoneapp.databinding.ActivityDetailBinding
import com.company.capstoneapp.reduceFileImage
import com.company.capstoneapp.rotateBitmap
import com.company.capstoneapp.ui.camera.CameraActivity
import com.company.capstoneapp.ui.camera.ResultCameraActivity
import com.company.capstoneapp.ui.maps.MapsActivity
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.File
import kotlin.math.floor

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var database: DatabaseReference
    private var isFavorite: Boolean = false
    private lateinit var latlng: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isFavorite = false

        binding.btnLike.setOnClickListener(this)

        binding.btnBack.setOnClickListener(this)

        val culinaryId = intent.getStringExtra(EXTRA_ID).toString()

        if (culinaryId.isNotEmpty()) {
            getCulinary(culinaryId)
        }

        binding.btnMaps.setOnClickListener(this)
    }

    fun getCulinary(culinaryId: String) {
        database = Firebase.database.reference.child("makanan")

        database.child(culinaryId).get().addOnSuccessListener {

            if (it.exists()) {
                // set image
                Glide.with(this)
                    .load(it.child("link").value)
                    .into(binding.itemImage)

                // set logo halal
                if (it.child("halal").value == false) {
                    Log.e("MAKANAN_HARAM", "INI HARAM " + it.child("name").value.toString())
                    binding.itemHalal.setImageResource(R.drawable.ic_nonhalal)
                }

                // set name & rate
                binding.itemName.text = it.child("name").value.toString()
                binding.itemRate.text = it.child("rate").value.toString()

                // set description
                val description = it.child("description").value.toString().replace("\\n", "\n")
                binding.itemDescription.text = description

                // set icon star
                var yellowStar = it.child("rate").value.toString().toDouble()
                yellowStar = floor(yellowStar)

                binding.icStar1.apply {
                    if (yellowStar < 1) {
                        setImageResource(R.drawable.ic_star_gray)
                    }
                }

                binding.icStar2.apply {
                    if (yellowStar < 2) {
                        setImageResource(R.drawable.ic_star_gray)
                    }
                }

                binding.icStar3.apply {
                    if (yellowStar < 3) {
                        setImageResource(R.drawable.ic_star_gray)
                    }
                }

                binding.icStar4.apply {
                    if (yellowStar < 4) {
                        setImageResource(R.drawable.ic_star_gray)
                    }
                }

                binding.icStar5.apply {
                    if (yellowStar < 5) {
                        setImageResource(R.drawable.ic_star_gray)
                    }
                }

                val lat = it.child("latitude").value.toString().toDouble()
                val lng = it.child("longitude").value.toString().toDouble()
                latlng = LatLng(lat, lng)

            } else {
                 // makanan tidak ditemukan
            }

        }.addOnFailureListener {
            Log.e("FIREBASE_GAGAL", it.toString())
        }
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.btn_back -> {
                finish()
            }
            R.id.btn_like -> {
                isFavorite = !isFavorite

                binding.btnLike.apply {
                    if (isFavorite) {
                        backgroundTintList = ContextCompat.getColorStateList(this@DetailActivity, R.color.like_red)
                        binding.btnLike.setImageResource(R.drawable.ic_favorite_checked)
                        Toast.makeText(this@DetailActivity, "Disukai", Toast.LENGTH_SHORT).show()
                    } else {
                        backgroundTintList = ContextCompat.getColorStateList(this@DetailActivity, R.color.like_gray)
                        binding.btnLike.setImageResource(R.drawable.ic_favorite_uncheck)
                        Toast.makeText(this@DetailActivity, "Batal Suka", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            R.id.btn_maps -> {
                Log.v("GANTENG", latlng.toString())
                val intent = Intent(this@DetailActivity, MapsActivity::class.java)
                intent.putExtra("latlng", latlng)
                startActivity(intent)
            }
        }
    }


    companion object {
        const val EXTRA_ID = "extra_id"
    }
}
