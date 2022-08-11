package com.lebudigital.lebudigital.model.users

import com.google.gson.annotations.SerializedName

data class UsersResponse(

	@field:SerializedName("provinsi")
	val provinsi: ProvinsiUsersModel? = null,

	@field:SerializedName("desa")
	val desa: DesaUsersModel? = null,

	@field:SerializedName("village_id")
	val villageId: String? = null,

	@field:SerializedName("role")
	val role: Int? = null,

	@field:SerializedName("foto_kk")
	val fotoKk: String? = null,

	@field:SerializedName("foto_akta")
	val foto_akta: String? = null,

	@field:SerializedName("foto")
	val foto: String? = null,

	@field:SerializedName("telepon")
	val telepon: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("email_verified_at")
	val emailVerifiedAt: Any? = null,

	@field:SerializedName("foto_ktp")
	val fotoKtp: String? = null,

	@field:SerializedName("alamat_lengkap")
	val alamatLengkap: String? = null,

	@field:SerializedName("kabupaten")
	val kabupaten: KabupatenUsersModel? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null,

	@field:SerializedName("nik")
	val nik: String? = null,

	@field:SerializedName("token_id")
	val tokenId: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("province_id")
	val provinceId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("kecamatan")
	val kecamatan: KecamatanUsersModel? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("district_id")
	val districtId: String? = null,

	@field:SerializedName("regencie_id")
	val regencieId: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)

data class DesaUsersModel(

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

data class ProvinsiUsersModel(

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

data class KecamatanUsersModel(

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

data class KabupatenUsersModel(

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
