package com.company.capstoneapp.ui.auth

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.company.capstoneapp.ApiConfig
import com.company.capstoneapp.DataUser
import com.company.capstoneapp.R
import com.company.capstoneapp.databinding.ActivityRegisterBinding
import com.company.capstoneapp.spannable
import com.company.capstoneapp.ui.home.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        binding.apply {
            btnSignup.setOnClickListener {
                if (etPassword.error != null || etEmail.error != null || etUsername.error != null || etVerifypassword.error != null) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Please fill the field with the right data",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                } else if (etPassword.text.toString() != etVerifypassword.text.toString()) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Your Password doesn't match",
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                } else {
                    val email = etEmail.text.toString().trim()
                    val password = etPassword.text.toString().trim()
                    val name = etUsername.text.toString()
                    handleSignUpResult(email, password, name)
                }
            }
        }
    }

    private fun handleSignUpResult(email: String, password: String, name: String) {
        ApiConfig.getApiService("https://identitytoolkit.googleapis.com/v1/").register(
            getString(R.string.API_KEY),
            email,
            password
        ).enqueue(object : Callback<DataUser> {
            override fun onResponse(
                call: Call<DataUser>,
                response: Response<DataUser>
            ) {
                if (response.isSuccessful) {
                    ApiConfig.getApiService("https://identitytoolkit.googleapis.com/v1/").changeName(
                        getString(R.string.API_KEY),
                        response.body()?.idToken,
                        name
                    ).enqueue(object : Callback<DataUser>{
                        override fun onResponse(
                            call: Call<DataUser>,
                            response: Response<DataUser>
                        ) {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Berhasil daftar dengan email: " + response.body()?.email,
                                Toast.LENGTH_LONG
                            ).show()
                            finishAffinity()
                            startActivity(
                                Intent(
                                    this@RegisterActivity, LoginActivity::class.java
                                )
                            )
                        }

                        override fun onFailure(call: Call<DataUser>, t: Throwable) {
                        }

                    })

                }
            }

            override fun onFailure(call: Call<DataUser>, t: Throwable) {
                Log.d(ContentValues.TAG, "ERROR :", t)
            }
        })
    }
}