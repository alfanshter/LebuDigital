package com.lebudigital.lebudigital.model.fasilitas

import com.google.gson.annotations.SerializedName
import com.lebudigital.lebudigital.model.profildesa.GambarDesaModel
import com.lebudigital.lebudigital.model.profildesa.ProfilModel

data class FasilitasResponse(

	@field:SerializedName("profil")
	val profil: ProfilModel? = null,

	@field:SerializedName("gambardesa")
	val gambardesa: List<GambarDesaModel>? = null,

	@field:SerializedName("fasilitas")
	val fasilitas: List<FasilitasModel>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

