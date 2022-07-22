package com.lebudigital.lebudigital.model.desaterdekat

import com.google.gson.annotations.SerializedName
import com.lebudigital.lebudigital.model.profildesa.Desa


data class DesaTerdekatModel(

    @field:SerializedName("desa_id")
    val desaId: String? = null,

    @field:SerializedName("desa")
    val desa: Desa? = null,

    @field:SerializedName("updated_at")
    val updatedAt: Any? = null,

    @field:SerializedName("distance")
    val distance: Double? = null,

    @field:SerializedName("province_id")
    val provinceId: String? = null,

    @field:SerializedName("kabupaten_id")
    val kabupatenId: String? = null,

    @field:SerializedName("latitude")
    val latitude: Double? = null,

    @field:SerializedName("created_at")
    val createdAt: Any? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("kecamatan_id")
    val kecamatanId: String? = null,

    @field:SerializedName("longitude")
    val longitude: Double? = null
)