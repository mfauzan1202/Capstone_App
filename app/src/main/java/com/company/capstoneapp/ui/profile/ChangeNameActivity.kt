package com.company.capstoneapp.ui.profile

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.company.capstoneapp.ApiConfig
import com.company.capstoneapp.DataUser
import com.company.capstoneapp.R
import com.company.capstoneapp.databinding.ActivityChangeNameBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeNameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangeNameBinding
    private lateinit var userData: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userData = getSharedPreferences("login_session", MODE_PRIVATE)

        binding.apply {
            btnSave.setOnClickListener {
                val pass = etPassword.text.toString().trim()
                val name = etUsername.text.toString()
                if (etUsername.error != null && etPassword.error != null) {
                    Toast.makeText(
                        this@ChangeNameActivity,
                        "Please fill the field with the right data",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                } else if (pass != userData.getString("password", null)) {
                    Toast.makeText(
                        this@ChangeNameActivity,
                        "Wrong Password",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                } else {
                    ApiConfig.getApiService("https://identitytoolkit.googleapis.com/v1/")
                        .changeName(
                            getString(R.string.API_KEY),
                            userData.getString("idToken", null),
                            name
                        ).enqueue(object : Callback<DataUser> {
                            override fun onResponse(
                                call: Call<DataUser>,
                                response: Response<DataUser>
                            ) {
                                getSharedPreferences("login_session", MODE_PRIVATE)
                                    .edit()
                                    .putString("name", response.body()?.displayName)
                                    .apply()
                                Toast.makeText(
                                    this@ChangeNameActivity,
                                    "Ubah nama berhasil",
                                    Toast.LENGTH_LONG
                                ).show()
                                finishAffinity()
                                startActivity(
                                    Intent(
                                        this@ChangeNameActivity, ProfileActivity::class.java
                                    )
                                )
                            }

                            override fun onFailure(call: Call<DataUser>, t: Throwable) {
                            }

                        })
                }
            }
        }
    }
}