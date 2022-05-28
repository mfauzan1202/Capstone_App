package com.fauzancode.capstoneapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.fauzancode.capstoneapp.R
import com.fauzancode.capstoneapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailBinding
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isFavorite = false

        binding.btnLike.setOnClickListener(this)

        binding.btnBack.setOnClickListener(this)
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
                        Toast.makeText(this@DetailActivity, "Batal Disukai", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}