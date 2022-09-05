package com.lebudigital.lebudigital.model.banner

import com.google.gson.annotations.SerializedName

data class BannerResponse(

	@field:SerializedName("data")
	val data: List<BannerModel>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

