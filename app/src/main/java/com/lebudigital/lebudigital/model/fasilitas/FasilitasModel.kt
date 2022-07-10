package com.lebudigital.lebudigital.model.fasilitas

import com.google.gson.annotations.SerializedName
import java.util.*


data class FasilitasModel(

    @field:SerializedName("village_id")
    val villageId: String? = null,

    @field:SerializedName("foto")
    val foto: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: Any? = null,

    @field:SerializedName("created_at")
    val createdAt: Date? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("fasilitas")
    val fasilitas: String? = null,

    @field:SerializedName("deskripsi")
    val deskripsi: String? = null
)