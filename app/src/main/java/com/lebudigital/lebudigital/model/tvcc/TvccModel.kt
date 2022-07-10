package com.lebudigital.lebudigital.model.tvcc

import com.google.gson.annotations.SerializedName
import java.util.*


data class TvccModel(

    @field:SerializedName("foto")
    val foto: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: Any? = null,

    @field:SerializedName("link")
    val link: String? = null,

    @field:SerializedName("narasi")
    val narasi: String? = null,

    @field:SerializedName("created_at")
    val createdAt: Date? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("judul")
    val judul: String? = null
)
