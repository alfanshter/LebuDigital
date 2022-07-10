package com.lebudigital.lebudigital.model.budayalokal

import com.google.gson.annotations.SerializedName

data class BudayaLokalResponse(

	@field:SerializedName("data")
	val data: List<BudayaLokalModel>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
