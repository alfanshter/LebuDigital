package com.lebudigital.lebudigital.model.users

import com.google.gson.annotations.SerializedName

data class ProfilResponse(

	@field:SerializedName("profil")
	val profil: UsersResponse? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
