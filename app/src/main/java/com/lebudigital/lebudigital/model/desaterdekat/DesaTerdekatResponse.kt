package com.lebudigital.lebudigital.model.desaterdekat

import com.google.gson.annotations.SerializedName

data class DesaTerdekatResponse(

	@field:SerializedName("data")
	val data: List<DesaTerdekatModel>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

