package com.lebudigital.lebudigital.model.auth

import com.google.gson.annotations.SerializedName

data class UpdataProfilResponse(

	@field:SerializedName("data")
	val data: UsersModel? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
