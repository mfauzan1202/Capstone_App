package com.company.capstoneapp

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @FormUrlEncoded
    @POST("token")
    fun getAccessToken(
        @Field("grant_type") grant_type: String?,
        @Field("client_id") client_id: String?,
        @Field("client_secret") client_secret: String?,
        @Field("code") code: String?
    ): Call<DataAccesInfo>

    @Headers("Content-Type: application/json")
    @POST("capstone-project-351416:lookup")
    fun getFood(
        @Body jsonKey: JsonObject
    ):Call<DataFood>
}