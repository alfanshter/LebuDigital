package com.lebudigital.lebudigital.model.auth

import com.google.gson.annotations.SerializedName

data class PostResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("user_id")
	val user_id: Int? = null
)
