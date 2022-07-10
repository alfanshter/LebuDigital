package com.lebudigital.lebudigital.model.auth

import com.google.gson.annotations.SerializedName

data class PostRegister(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("village_id")
	val villageId: String? = null,

	@field:SerializedName("province_id")
	val provinceId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("district_id")
	val districtId: String? = null,

	@field:SerializedName("regencie_id")
	val regencieId: String? = null,

	@field:SerializedName("alamat_lengkap")
	val alamatLengkap: String? = null,

	@field:SerializedName("email")
	val email: String? = null,
	@field:SerializedName("telepon")
	val telepon: String? = null
)
