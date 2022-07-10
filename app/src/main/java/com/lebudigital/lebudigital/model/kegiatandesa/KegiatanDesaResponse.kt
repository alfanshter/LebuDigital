package com.lebudigital.lebudigital.model.profildesa

import com.google.gson.annotations.SerializedName
import com.lebudigital.lebudigital.model.kegiatandesa.KegiatanDesaModel

data class KegiatanDesaResponse(

	@field:SerializedName("kegiatandesa")
	val kegiatandesa: List<KegiatanDesaModel>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
