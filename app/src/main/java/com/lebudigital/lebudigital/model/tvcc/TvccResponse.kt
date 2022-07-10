package com.lebudigital.lebudigital.model.tvcc

import com.google.gson.annotations.SerializedName

data class TvccResponse(

	@field:SerializedName("data")
	val data: List<TvccModel>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
