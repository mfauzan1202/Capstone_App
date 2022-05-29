package com.company.capstoneapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.company.capstoneapp.R
import com.company.capstoneapp.databinding.ActivityProfileBinding


class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

}