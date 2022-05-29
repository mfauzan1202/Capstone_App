package com.company.capstoneapp.ui.home

import android.content.ContentValues.TAG
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.company.capstoneapp.ApiConfig
import com.company.capstoneapp.DataFood
import com.company.capstoneapp.R
import com.company.capstoneapp.databinding.ActivityHomeBinding
import com.company.capstoneapp.ui.adapter.ListCulinaryAroundAdapter
import com.company.capstoneapp.ui.adapter.ListCulinaryRecommendationAdapter
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        binding.rvCulinaryAround.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = ListCulinaryAroundAdapter()
        }

        binding.rvRecommendation.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = ListCulinaryRecommendationAdapter()
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
