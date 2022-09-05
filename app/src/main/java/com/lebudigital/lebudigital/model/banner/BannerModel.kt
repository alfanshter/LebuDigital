package com.lebudigital.lebudigital.model.banner

import com.google.gson.annotations.SerializedName
import java.sql.Date
import java.sql.Timestamp
import java.time.LocalDateTime

data class BannerModel(

    @field:SerializedName("village_id")
    val villageId: String? = null,

    @field:SerializedName("role")
    val role: Int? = null,

    @field:SerializedName("foto")
    val foto: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: Any? = null,

    @field:SerializedName("province_id")
    val provinceId: String? = null,

    @field:SerializedName("link")
    val link: String? = null,

    @field:SerializedName("created_at")
    val createdAt: Timestamp? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("deskripsi")
    val deskripsi: String? = null,

    @field:SerializedName("district_id")
    val districtId: String? = null,

    @field:SerializedName("judul")
    val judul: String? = null,

    @field:SerializedName("regencie_id")
    val regencieId: String? = null
)
