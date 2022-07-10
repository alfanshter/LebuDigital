package com.lebudigital.lebudigital.model.geospasial

import com.google.gson.annotations.SerializedName

data class GeoSpasialResponse(

	@field:SerializedName("desa")
	val desa: GeoSpasialModel? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
