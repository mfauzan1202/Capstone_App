package com.company.capstoneapp

import com.google.gson.annotations.SerializedName

data class DataAccesInfo(

	@field:SerializedName("access_token")
	val accessToken: String,

	@field:SerializedName("refresh_token")
	val refreshToken: String,

	@field:SerializedName("id_token")
	val idToken: String,

	@field:SerializedName("token_type")
	val tokenType: String,

	@field:SerializedName("expires_in")
	val expiresIn: Int
)
