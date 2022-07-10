package com.lebudigital.lebudigital.model.wilayah

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.math.BigInteger


class ResponseWilayah{
    @Expose
    @SerializedName("data")
    var data: List<Provinsi> = ArrayList()


    class Provinsi {
        var id: Int? = null
        var name: String? = null
        override fun toString(): String {
            return name!!
        }

    }
}

class ResponseKota{
    @Expose
    @SerializedName("data")
    var data: List<Kota> = ArrayList()

    class Kota{
        var id: Int? = null
        var id_provinsi : Int? = null
        var name : String? = null
        override fun toString(): String {
            return name!!
        }
    }
}

class ResponseKecamatan{
    @Expose
    @SerializedName("data")
    var data : List<Kecamatan> = ArrayList()

    class Kecamatan{
        var id : Int?  =null
        var id_kabupaten : Int? = null
        var name : String? = null
        override fun toString(): String {
            return name!!
        }
    }
}

class ResponseKelurahan{
    @Expose
    @SerializedName("data")
    var data : List<Kelurahan> = ArrayList()

    class Kelurahan{
        var id : BigInteger? = null
        var id_kecamatan : Int? = null
        var name : String? = null

        override fun toString(): String {
            return name!!
        }
    }
}

