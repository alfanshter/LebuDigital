package com.lebudigital.lebudigital.model.profildesa

import com.google.gson.annotations.SerializedName

data class ProfilResponse(

	@field:SerializedName("data_statistik")
	val dataStatistik: List<DataStatistikModel>? = null,

	@field:SerializedName("gambar_desa")
	val gambarDesa: List<GambarDesaModel>? = null,

	@field:SerializedName("profil")
	val profil: ProfilModel? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

