package com.lebudigital.lebudigital.model.kegiatandesa

import com.google.gson.annotations.SerializedName
import java.util.*

data class KegiatanDesaModel(

    @field:SerializedName("village_id")
    val villageId: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("nama_kegiatan")
    val namaKegiatan: String? = null,

    @field:SerializedName("jam")
    val jam: String? = null,

    @field:SerializedName("latitude")
    val latitude: String? = null,

    @field:SerializedName("created_at")
    val createdAt: Any? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("tanggal")
    val tanggal: Date? = null,

    @field:SerializedName("kegiatan_desa")
    val kegiatanDesa: String? = null,

    @field:SerializedName("alamat")
    val alamat: String? = null,

    @field:SerializedName("longitude")
    val longitude: String? = null,

    @field:SerializedName("video")
    val video: String? = null,

    @field:SerializedName("foto")
    val foto: String? = null
)
