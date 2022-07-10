package com.lebudigital.lebudigital.model.versi

import com.google.gson.annotations.SerializedName

data class VersiResponse(

	@field:SerializedName("data")
	val data: VersiModel? = null,

	@field:SerializedName("message")
	val message: String? = null
)
