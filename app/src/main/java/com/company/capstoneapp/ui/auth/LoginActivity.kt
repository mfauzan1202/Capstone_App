package com.company.capstoneapp.ui.auth

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.company.capstoneapp.ApiConfig
import com.company.capstoneapp.DataUser
import com.company.capstoneapp.R
import com.company.capstoneapp.databinding.ActivityLoginBinding
import com.company.capstoneapp.spannable
import com.company.capstoneapp.ui.home.HomeActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_GET_AUTH_CODE = 9003

    private lateinit var userData: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userData = getSharedPreferences("login_session", MODE_PRIVATE)
        if (userData.getString("localId", null) != null) {
            finishAffinity()
            startActivity(Intent(this, HomeActivity::class.java))
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode("333452533498-qkgrk3qb93r8ru4ruia4cgm3htutanpo.apps.googleusercontent.com")
            .requestProfile()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.apply {
            //sign in with google
            googleSignInButton.setOnClickListener {
                val signInIntent = mGoogleSignInClient.signInIntent
                startActivityForResult(
                    signInIntent, RC_GET_AUTH_CODE
                )
            }

            //sign in with our service
            btnSignin.setOnClickListener {
                if (etPassword.error != null || etEmail.error != null) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please fill the field with the right data",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                } else {
                    handleSignInResult(
                        etEmail.text.toString().trim(),
                        etPassword.text.toString().trim()
                    )
                }
            }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_GET_AUTH_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)

        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        val account = completedTask.getResult(
            ApiException::class.java
        )
        getSharedPreferences("login_session", MODE_PRIVATE)
            .edit()
            .putString("userId", account.id.toString())
            .putString("name", account.displayName.toString())
            .putString("email", account.email.toString())
            .putString("urlPhoto", account.photoUrl.toString())
            .apply()
        finishAffinity()
        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
//                Log.e(
//                    "failed code=", ApiException.statusCode.toString()
//                )
    }

    private fun handleSignInResult(email: String, password: String) {
        ApiConfig.getApiService("https://identitytoolkit.googleapis.com/v1/").login(
            getString(R.string.API_KEY),
            email,
            password
        ).enqueue(object : Callback<DataUser> {
            override fun onResponse(
                call: Call<DataUser>,
                response: Response<DataUser>
            ) {
                if (response.isSuccessful) {

                    val responseBody = response.body()

                    if (responseBody != null) {
                        val dataUser: DataUser? = responseBody
                        getSharedPreferences("login_session", MODE_PRIVATE)
                            .edit()
                            .putString("localId", dataUser?.localId)
                            .putString("email", dataUser?.email)
                            .putString("name", dataUser?.displayName)
                            .putString("idToken", dataUser?.displayName)
                            .apply()
                        Toast.makeText(
                            this@LoginActivity,
                            response.body()?.email,
                            Toast.LENGTH_SHORT
                        ).show()
                        finishAffinity()
                        startActivity(
                            Intent(
                                this@LoginActivity,
                                HomeActivity::class.java
                            )
                        )
                    }
                }
            }

            override fun onFailure(call: Call<DataUser>, t: Throwable) {
                Log.d(TAG, "ERROR :", t)
            }
        })
    }
}
