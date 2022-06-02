package com.company.capstoneapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.company.capstoneapp.R
import com.company.capstoneapp.databinding.ActivityHomeMapBinding

class HomeMapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener{
            startActivity(Intent(this@HomeMapActivity, HomeActivity::class.java))
        }


    }
}