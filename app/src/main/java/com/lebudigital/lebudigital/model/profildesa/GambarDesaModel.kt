package com.lebudigital.lebudigital.model.profildesa

import com.google.gson.annotations.SerializedName


data class GambarDesaModel(

    @field:SerializedName("village_id")
    val villageId: String? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("foto")
    val foto: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: Any? = null,

    @field:SerializedName("created_at")
    val createdAt: Any? = null,

    @field:SerializedName("id")
    val id: Int? = null
)
