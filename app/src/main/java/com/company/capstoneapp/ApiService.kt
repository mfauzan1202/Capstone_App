package com.company.capstoneapp

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query


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
    @POST("./accounts:signUp")//karna ada semicolon(:) jadi pake (./)
    fun register(
        @Query("key") query: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("returnSecureToken") bool: Boolean = true,
    ): Call<DataUser>

    @FormUrlEncoded
    @POST("./accounts:signInWithPassword")//karna ada semicolon(:) jadi pake (./)
    fun login(
        @Query("key") query: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("returnSecureToken") bool: Boolean = true,
    ): Call<DataUser>

    @FormUrlEncoded
    @POST("./accounts:update")//karna ada semicolon(:) jadi pake (./)
    fun changeName(
        @Query("key") query: String,
        @Field("idToken") idToken: String?,
        @Field("displayName") displayName: String,
        @Field("returnSecureToken") bool: Boolean = true,
    ): Call<DataUser>

    @FormUrlEncoded
    @POST("./accounts:update")//karna ada semicolon(:) jadi pake (./)
    fun changePass(
        @Query("key") query: String,
        @Field("idToken") idToken: String?,
        @Field("password") password: String,
        @Field("returnSecureToken") bool: Boolean = true,
    ): Call<DataUser>
}