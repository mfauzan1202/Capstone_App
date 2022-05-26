package com.fauzancode.capstoneapp.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fauzancode.capstoneapp.databinding.ActivityLoginBinding
import com.fauzancode.capstoneapp.spannable
import com.fauzancode.capstoneapp.ui.HomeActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignin.setOnClickListener {
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        }

        val tvsignup = binding.tvSignup
        val text = "Belum punya akun? Daftar!"
        spannable(
            tvsignup,
            text,
            18,
            25,
            this@LoginActivity,
            Intent(this@LoginActivity, RegisterActivity::class.java)
        )
    }
}