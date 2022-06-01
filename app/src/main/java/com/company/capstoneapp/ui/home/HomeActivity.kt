package com.company.capstoneapp.ui.home

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.company.capstoneapp.R
import com.company.capstoneapp.databinding.ActivityHomeBinding
import com.company.capstoneapp.dataclass.Culinary
import com.company.capstoneapp.ui.adapter.ListCulinaryAroundAdapter
import com.company.capstoneapp.ui.adapter.ListCulinaryRecommendationAdapter
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database.reference

        userInfo = getSharedPreferences("login_session", MODE_PRIVATE)
        binding.apply {
            val fullname = userInfo.getString("name", "Unknown").toString()
            val firstName = fullname.split(" ")[0]

            navbar.itemIconTintList = null
            tvUsername.text =
                resources.getString(
                    R.string.name_placeholder,
                    firstName
                )

            Glide.with(this@HomeActivity)
                .load(userInfo.getString("urlPhoto", "https://cdn-asset.jawapos.com/wp-content/uploads/2021/03/37_jokowi_cabut_aturan-560x432.jpg"))
                .into(ivAvatar)

            btnMaps.setOnClickListener {
                // TODO: ganti ke MapsActivity
                startActivity(Intent(this@HomeActivity, ProfileActivity::class.java))
            }

        }

        // recyclerview makanan disekitar
        val managerCulinaryAroundAdapter = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        managerCulinaryAroundAdapter.stackFromEnd = false
        binding.rvCulinaryAround.layoutManager = managerCulinaryAroundAdapter
        binding.rvCulinaryAround.itemAnimator = null

        val options = FirebaseRecyclerOptions.Builder<Culinary>()
            .setQuery(database.child("makanan"), Culinary::class.java)
            .build()

        culinaryAroundAdapter = ListCulinaryAroundAdapter(options)
        binding.rvCulinaryAround.adapter = culinaryAroundAdapter

        // recyclerview makanan rekomendasi
        binding.rvRecommendation.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = ListCulinaryRecommendationAdapter()
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
