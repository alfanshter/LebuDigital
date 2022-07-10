package com.lebudigital.lebudigital.model.profildesa

import com.google.gson.annotations.SerializedName

data class ProfilModel(

	@field:SerializedName("provinsi")
	val provinsi: Provinsi? = null,

	@field:SerializedName("desa")
	val desa: Desa? = null,

	@field:SerializedName("village_id")
	val villageId: String? = null,

	@field:SerializedName("kota")
	val kota: Kota? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("kepala_desa")
	val kepalaDesa: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("sekretaris_desa")
	val sekretarisDesa: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("province_id")
	val provinceId: String? = null,

	@field:SerializedName("kecamatan")
	val kecamatan: Kecamatan? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("district_id")
	val districtId: String? = null,

	@field:SerializedName("regencie_id")
	val regencieId: String? = null
)

data class Kota(

	@field:SerializedName("is_status")
	val isStatus: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class Provinsi(

	@field:SerializedName("is_status")
	val isStatus: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class Kecamatan(

	@field:SerializedName("is_status")
	val isStatus: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class Desa(

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
