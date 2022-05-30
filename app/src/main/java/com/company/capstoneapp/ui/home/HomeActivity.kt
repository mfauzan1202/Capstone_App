package com.company.capstoneapp.ui.home

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.company.capstoneapp.R
import com.company.capstoneapp.databinding.ActivityHomeBinding
import com.company.capstoneapp.ui.adapter.ListCulinaryAroundAdapter
import com.company.capstoneapp.ui.adapter.ListCulinaryRecommendationAdapter
import com.company.capstoneapp.ui.profile.ProfileActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var userInfo: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userInfo = getSharedPreferences("login_session", MODE_PRIVATE)
        binding.apply {
            navbar.itemIconTintList = null
            tvUsername.text =
                resources.getString(
                    R.string.name_placeholder,
                    userInfo.getString("name", "Unknown")
                )
            Glide.with(this@HomeActivity)
                .load(userInfo.getString("urlPhoto", null))
                .into(ivAvatar)

            btnMaps.setOnClickListener {
                //TODO: ini button maps jangan lupa diganti intentnya ke map lagi
                startActivity(Intent(this@HomeActivity, ProfileActivity::class.java))
            }
        }

        binding.rvCulinaryAround.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = ListCulinaryAroundAdapter()
        }

        binding.rvRecommendation.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = ListCulinaryRecommendationAdapter()
        }
    }
}
