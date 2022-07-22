package com.lebudigital.lebudigital.model.login

import com.google.gson.annotations.SerializedName
import com.lebudigital.lebudigital.model.auth.UsersModel

data class LoginResponse(

	@field:SerializedName("data")
	val data: UsersModel? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
