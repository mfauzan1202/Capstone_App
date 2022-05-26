package com.fauzancode.capstoneapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import com.fauzancode.capstoneapp.R
import com.fauzancode.capstoneapp.databinding.ActivityLoginBinding
import com.fauzancode.capstoneapp.spannable

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

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