package com.lebudigital.lebudigital.model.beritadesa

import com.google.gson.annotations.SerializedName

data class BeritaDesaResponse(

	@field:SerializedName("data")
	val data: List<BeritaDesaModel>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
