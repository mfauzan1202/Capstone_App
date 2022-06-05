package com.company.capstoneapp.ui.home

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.company.capstoneapp.R
import com.company.capstoneapp.databinding.ActivityHomeBinding
import com.company.capstoneapp.dataclass.Culinary
import com.company.capstoneapp.ui.adapter.ListCulinaryAroundAdapter
import com.company.capstoneapp.ui.adapter.ListCulinaryRecommendationAdapter
import com.company.capstoneapp.ui.camera.ResultCameraActivity
import com.company.capstoneapp.ui.profile.ProfileActivity
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var userInfo: SharedPreferences
    private lateinit var database: DatabaseReference
    private lateinit var culinaryAroundAdapter: ListCulinaryAroundAdapter

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        database = Firebase.database.reference

        userInfo = getSharedPreferences("login_session", MODE_PRIVATE)
        binding.apply {
            val fullname = userInfo.getString("name", "Unknown").toString()
            val firstName = fullname.split(" ")[0]

            tvUsername.text =
                resources.getString(
                    R.string.name_placeholder,
                    firstName
                )

            Glide.with(this@HomeActivity)
                .load(
                    userInfo.getString(
                        "urlPhoto",
                        null
                    )
                )
                .into(ivAvatar)

            btnMaps.setOnClickListener {
                startActivity(Intent(this@HomeActivity, HomeMapActivity::class.java))
            }

            floatingActionButton.setOnClickListener {
                startActivity(Intent(this@HomeActivity, ResultCameraActivity::class.java))
            }

            // recyclerview makanan disekitar
            val managerCulinaryAroundAdapter =
                LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
            managerCulinaryAroundAdapter.stackFromEnd = false
            rvCulinaryAround.layoutManager = managerCulinaryAroundAdapter
            rvCulinaryAround.itemAnimator = null

            val options = FirebaseRecyclerOptions.Builder<Culinary>()
                .setQuery(database.child("makanan"), Culinary::class.java)
                .build()

            culinaryAroundAdapter = ListCulinaryAroundAdapter(options)
            rvCulinaryAround.adapter = culinaryAroundAdapter

            // recyclerview makanan rekomendasi
            rvRecommendation.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@HomeActivity)
                adapter = ListCulinaryRecommendationAdapter()
            }

            navbar.selectedItemId = R.id.home_menu
            navbar.menu.findItem(R.id.profile_menu).setOnMenuItemClickListener {
                startActivity(Intent(this@HomeActivity, ProfileActivity::class.java))
                return@setOnMenuItemClickListener false
            }
        }
    }

    public override fun onResume() {
        super.onResume()
        culinaryAroundAdapter.startListening()
    }

    public override fun onPause() {
        culinaryAroundAdapter.stopListening()
        super.onPause()
    }
}
