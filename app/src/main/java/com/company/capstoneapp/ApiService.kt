package com.company.capstoneapp

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File


interface ApiService {

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
    fun changeProfile(
        @Query("key") query: String,
        @Field("idToken") idToken: String?,
        @Field("displayName") displayName: String? = null,
        @Field("password") password: String? = null,
        @Field("photoUrl") photoUrl: String? = null,
        @Field("returnSecureToken") bool: Boolean = true,
    ): Call<DataUser>

    @FormUrlEncoded
    @POST("token")
    fun refreshIdToken(
        @Query("key") query: String,
        @Field("refresh_token") displayName: String,
        @Field("grant_type") idToken: String = "refresh_token",
    ): Call<DataUser>

    @Multipart
    @POST("predict")
    fun uploadImage(
        @Part file: MultipartBody.Part
    ): Call<DetectionResponse>
}