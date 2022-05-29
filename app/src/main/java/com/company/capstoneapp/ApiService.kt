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

    @FormUrlEncoded
    @POST("./accounts:signInWithPassword")//karna ada semicolon(:) jadi pake (./)
    fun login(
        @Query("key") query: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("returnSecureToken") bool: Boolean = true,
    ): Call<DataUser>
}