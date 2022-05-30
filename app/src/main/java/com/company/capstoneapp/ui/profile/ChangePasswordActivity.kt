package com.company.capstoneapp.ui.profile

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.company.capstoneapp.ApiConfig
import com.company.capstoneapp.DataUser
import com.company.capstoneapp.R
import com.company.capstoneapp.databinding.ActivityChangePasswordBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var userData: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userData = getSharedPreferences("login_session", MODE_PRIVATE)

        binding.apply {
            btnSave.setOnClickListener {
                val oldPass = etOldpassword.text.toString().trim()
                val newPass = etNewpassword.text.toString().trim()
                val verifyNewPass = etVerifyNewpassword.text.toString().trim()
                if (etOldpassword.error != null && etNewpassword.error != null && etVerifyNewpassword.error != null) {
                    Toast.makeText(
                        this@ChangePasswordActivity,
                        "Please fill the field with the right data",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                } else if (oldPass != userData.getString("password", null)) {
                    Toast.makeText(
                        this@ChangePasswordActivity,
                        "Password Salah",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                } else if (newPass != verifyNewPass) {
                    Toast.makeText(
                        this@ChangePasswordActivity,
                        "Password yang anda masukkan tidak sama",
                        Toast.LENGTH_SHORT
                    ).show()
                    tiVerifyNewpassword.error = "Password yang anda masukkan tidak sama"
                    etVerifyNewpassword.requestFocus()
                    return@setOnClickListener
                } else {
                    ApiConfig.getApiService("https://identitytoolkit.googleapis.com/v1/")
                        .changePass(
                            getString(R.string.API_KEY),
                            userData.getString("idToken", null),
                            newPass
                            ).enqueue(object : Callback<DataUser> {
                            override fun onResponse(
                                call: Call<DataUser>,
                                response: Response<DataUser>
                            ) {
                                getSharedPreferences("login_session", MODE_PRIVATE)
                                    .edit()
                                    .putString("password", newPass)
                                    .apply()
                                Toast.makeText(
                                    this@ChangePasswordActivity,
                                    "Ubah Pass Berhasil",
                                    Toast.LENGTH_LONG
                                ).show()
                                finishAffinity()
                                startActivity(
                                    Intent(
                                        this@ChangePasswordActivity, ProfileActivity::class.java
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