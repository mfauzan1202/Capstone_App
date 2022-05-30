package com.company.capstoneapp.ui.profile

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.company.capstoneapp.R
import com.company.capstoneapp.databinding.ActivityProfileBinding
import com.company.capstoneapp.ui.auth.LoginActivity


class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var userData: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userData = getSharedPreferences("login_session", MODE_PRIVATE)

        binding.apply {

            tvChangeName.setOnClickListener{
                startActivity(Intent(this@ProfileActivity, ChangeNameActivity::class.java))
            }

            tvChangePassword.setOnClickListener{
                startActivity(Intent(this@ProfileActivity, ChangePasswordActivity::class.java))
            }

            btnSignout.setOnClickListener {
                userData.edit().clear().apply()
                val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

}