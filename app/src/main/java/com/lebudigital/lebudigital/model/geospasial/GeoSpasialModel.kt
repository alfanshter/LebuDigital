package com.lebudigital.lebudigital.model.geospasial

import com.google.gson.annotations.SerializedName


data class GeoSpasialModel(

    @field:SerializedName("desa_id")
    val desaId: String? = null,

    @field:SerializedName("desa")
    val desa: GeoDesaModel? = null,

    @field:SerializedName("updated_at")
    val updatedAt: Any? = null,

    @field:SerializedName("province_id")
    val provinceId: String? = null,

    @field:SerializedName("kabupaten_id")
    val kabupatenId: String? = null,

    @field:SerializedName("latitude")
    val latitude: Double? = null,

    @field:SerializedName("created_at")
    val createdAt: Any? = null,

    @field:SerializedName("id")
    val id: Long? = null,

    @field:SerializedName("kecamatan_id")
    val kecamatanId: String? = null,

    @field:SerializedName("longitude")
    val longitude: Double? = null,

    @field:SerializedName("is_status")
    val isStatus: Int? = null,

    @field:SerializedName("name")
    val name: String? = null
)
