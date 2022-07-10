package com.lebudigital.lebudigital.model.geospasial

import com.google.gson.annotations.SerializedName

data class GeoDesaModel(

	@field:SerializedName("is_status")
	val isStatus: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: Any? = null,

	@field:SerializedName("id")
	val id: Long? = null
)
