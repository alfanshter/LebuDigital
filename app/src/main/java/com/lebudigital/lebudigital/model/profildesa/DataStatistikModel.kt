package com.lebudigital.lebudigital.model.profildesa

import com.google.gson.annotations.SerializedName


data class DataStatistikModel(

    @field:SerializedName("village_id")
    val villageId: String? = null,

    @field:SerializedName("jumlah")
    val jumlah: Int? = null,

    @field:SerializedName("updated_at")
    val updatedAt: Any? = null,

    @field:SerializedName("province_id")
    val provinceId: String? = null,

    @field:SerializedName("jenis")
    val jenis: String? = null,

    @field:SerializedName("created_at")
    val createdAt: Any? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("district_id")
    val districtId: String? = null,

    @field:SerializedName("regencie_id")
    val regencieId: String? = null
)