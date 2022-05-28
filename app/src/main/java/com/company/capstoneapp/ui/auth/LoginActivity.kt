package com.company.capstoneapp.ui.auth

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.company.capstoneapp.DataAccesInfo
import com.company.capstoneapp.ApiConfig
import com.company.capstoneapp.databinding.ActivityLoginBinding
import com.company.capstoneapp.spannable
import com.company.capstoneapp.ui.home.HomeActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_GET_AUTH_CODE = 9003

    private lateinit var userData: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userData = getSharedPreferences("login_session", MODE_PRIVATE)
        if (userData.getString("userId", null) != null) {
            finishAffinity()
            startActivity(Intent(this, HomeActivity::class.java))
        }

        binding.btnSignin.setOnClickListener {
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode("333452533498-qkgrk3qb93r8ru4ruia4cgm3htutanpo.apps.googleusercontent.com")
            .requestProfile()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.googleSignInButton.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(
                signInIntent, RC_GET_AUTH_CODE
            )
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
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)
            handleSignInResult(task)
            
            Log.d(TAG, "onActivityResult:GET_AUTH_CODE:success:" + result!!.status.isSuccess)
            if (result.isSuccess) {

                val acct = result.signInAccount
                val authCode = acct?.serverAuthCode ?: ""

                ApiConfig.getApiService("https://oauth2.googleapis.com/")
                    .getAccessToken(
                        "authorization_code",
                        "333452533498-qkgrk3qb93r8ru4ruia4cgm3htutanpo.apps.googleusercontent.com",
                        "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",
                        authCode
                    ).enqueue(object : Callback<DataAccesInfo> {

                        override fun onResponse(call: Call<DataAccesInfo>, response: Response<DataAccesInfo>) {
                            try {
                                val jsonObject = JSONObject(response.body().toString())
                                val message: String = jsonObject.toString(5)
                                Log.i(TAG, message)
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }
                        override fun onFailure(call: Call<DataAccesInfo>, t: Throwable) {
                        }
                    })
            }
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
}
