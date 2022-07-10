package com.lebudigital.lebudigital.model.budayalokal

import com.google.gson.annotations.SerializedName
import java.util.*


data class BudayaLokalModel(

    @field:SerializedName("tanggal_terbit")
    val tanggalTerbit: Date? = null,

    @field:SerializedName("village_id")
    val villageId: String? = null,

    @field:SerializedName("foto")
    val foto: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("cagar_budaya")
    val cagarBudaya: String? = null,

    @field:SerializedName("uraian")
    val uraian: String? = null,

    @field:SerializedName("created_at")
    val createdAt: Any? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("video")
    val video: String? = null,

    @field:SerializedName("judul")
    val judul: String? = null
)
