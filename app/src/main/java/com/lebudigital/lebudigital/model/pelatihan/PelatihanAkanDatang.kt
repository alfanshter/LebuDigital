package com.lebudigital.lebudigital.model.pelatihan

import com.google.gson.annotations.SerializedName


data class PelatihanAkanDatang(

    @field:SerializedName("village_id")
    val villageId: String? = null,

    @field:SerializedName("created_at")
    val createdAt: Any? = null,

    @field:SerializedName("video")
    val video: String? = null,

    @field:SerializedName("nara_sumber")
    val naraSumber: String? = null,

    @field:SerializedName("foto")
    val foto: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("kontak_person")
    val kontakPerson: String? = null,

    @field:SerializedName("lokasi_kegiatan")
    val lokasiKegiatan: String? = null,

    @field:SerializedName("link_pendaftaran")
    val linkPendaftaran: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("deskripsi")
    val deskripsi: String? = null,

    @field:SerializedName("tanggal")
    val tanggal: String? = null,

    @field:SerializedName("judul")
    val judul: String? = null
)