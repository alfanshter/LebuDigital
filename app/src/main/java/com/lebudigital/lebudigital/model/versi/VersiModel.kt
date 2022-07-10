package com.lebudigital.lebudigital.model.versi

import com.google.gson.annotations.SerializedName


data class VersiModel(

    @field:SerializedName("versi_aplikasi")
    val versiAplikasi: Int? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("nama_aplikasi")
    val namaAplikasi: String? = null,

    @field:SerializedName("link_download")
    val linkDownload: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)
