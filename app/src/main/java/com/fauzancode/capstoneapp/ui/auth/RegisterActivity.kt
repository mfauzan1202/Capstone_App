package com.fauzancode.capstoneapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fauzancode.capstoneapp.R
import com.fauzancode.capstoneapp.databinding.ActivityLoginBinding
import com.fauzancode.capstoneapp.databinding.ActivityRegisterBinding
import com.fauzancode.capstoneapp.spannable

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tvsignin = binding.tvSignin
        val text = "Sudah punya akun? Masuk!"
        spannable(
            tvsignin,
            text,
            18,
            24,
            this@RegisterActivity,
            Intent(this@RegisterActivity, LoginActivity::class.java)
        )
    }
}