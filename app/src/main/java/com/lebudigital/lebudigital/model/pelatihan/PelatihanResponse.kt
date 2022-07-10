package com.lebudigital.lebudigital.model.pelatihan

import com.google.gson.annotations.SerializedName

data class PelatihanResponse(

	@field:SerializedName("pelatihan_minggu_ini")
	val pelatihanMingguIni: List<PelatihanMinggu>? = null,

	@field:SerializedName("pelatihan_akan_datang")
	val pelatihanAkanDatang: List<PelatihanMinggu>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

