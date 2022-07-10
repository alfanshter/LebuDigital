package com.lebudigital.lebudigital.model.beritadesa

import com.google.gson.annotations.SerializedName
import java.util.*


data class BeritaDesaModel(

    @field:SerializedName("tanggal_terbit")
    val tanggalTerbit: Date? = null,

    @field:SerializedName("village_id")
    val villageId: String? = null,

    @field:SerializedName("created_at")
    val createdAt: Any? = null,

    @field:SerializedName("video")
    val video: String? = null,

    @field:SerializedName("foto")
    val foto: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: Any? = null,

    @field:SerializedName("province_id")
    val provinceId: String? = null,

    @field:SerializedName("narasi")
    val narasi: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("district_id")
    val districtId: String? = null,

    @field:SerializedName("judul")
    val judul: String? = null,

    @field:SerializedName("regencie_id")
    val regencieId: String? = null,

    @field:SerializedName("dikunjungi")
    val dikunjungi: Any? = null
)
