package com.company.capstoneapp.ui.home

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.company.capstoneapp.R
import com.company.capstoneapp.databinding.ActivityHomeBinding
import com.google.gson.JsonArray
import com.google.gson.JsonObject

class HomeActivity : AppCompatActivity(){

    private lateinit var binding: ActivityHomeBinding
    private lateinit var userInfo: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userInfo = getSharedPreferences("login_session", MODE_PRIVATE)
        binding.apply {
            navbar.itemIconTintList = null
            tvUsername.text =
                resources.getString(R.string.name_placeholder, userInfo.getString("name", "Unknown"))
            Glide.with(this@HomeActivity)
                .load(userInfo.getString("urlPhoto", null))
                .into(ivAvatar)
        }

        val jsonObject = JsonObject()
        jsonObject.addProperty("kind", "food")
        jsonObject.addProperty("name", "bika_ambon")

        val jsonPath = JsonObject()
        val pathArray = JsonArray()
        pathArray.add(jsonObject)
        jsonPath.add("path", pathArray)

        val jsonKey = JsonObject()
        val keyArray = JsonArray()
        keyArray.add(jsonPath)
        jsonKey.add("key", keyArray)

//        val dataFood = MutableLiveData<DataFood>()
//        ApiConfig.getApiService("https://datastore.googleapis.com/v1/projects/")
//            .getFood(jsonKey).enqueue(object : Callback<DataFood>{
//                override fun onResponse(call: Call<DataFood>, response: Response<DataFood>) {
//                    if (response.isSuccessful) {
//                        Log.d( TAG, response.body().toString())
//                        dataFood.postValue(response.body())
//                    }
//
//                }
//
//                override fun onFailure(call: Call<DataFood>, t: Throwable) {
//                }
//
//            })
    }
}