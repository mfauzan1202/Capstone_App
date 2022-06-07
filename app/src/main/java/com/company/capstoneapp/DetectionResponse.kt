package com.company.capstoneapp

import com.google.gson.annotations.SerializedName

data class DetectionResponse(

	@field:SerializedName("status")
	val status: Boolean,

	@field:SerializedName("makanan")
	val makanan: String
)
