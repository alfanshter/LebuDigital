package com.lebudigital.lebudigital.model.chatpencarian

import com.google.gson.annotations.SerializedName


data class PencarianUserModel(

    @field:SerializedName("village_id")
    val villageId: String? = null,

    @field:SerializedName("role")
    val role: Int? = null,

    @field:SerializedName("foto_kk")
    val fotoKk: Any? = null,

    @field:SerializedName("telepon")
    val telepon: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("email_verified_at")
    val emailVerifiedAt: Any? = null,

    @field:SerializedName("foto_ktp")
    val fotoKtp: Any? = null,

    @field:SerializedName("alamat_lengkap")
    val alamatLengkap: String? = null,

    @field:SerializedName("alamat")
    val alamat: Any? = null,

    @field:SerializedName("nik")
    val nik: Any? = null,

    @field:SerializedName("token_id")
    val tokenId: Any? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("province_id")
    val provinceId: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("district_id")
    val districtId: String? = null,

    @field:SerializedName("regencie_id")
    val regencieId: String? = null,

    @field:SerializedName("email")
    val email: String? = null
)
