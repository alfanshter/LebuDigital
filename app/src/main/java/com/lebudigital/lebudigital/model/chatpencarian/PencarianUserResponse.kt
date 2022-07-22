package com.lebudigital.lebudigital.model.chatpencarian

import com.google.gson.annotations.SerializedName

data class PencarianUserResponse(

	@field:SerializedName("data")
	val data: List<PencarianUserModel>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
