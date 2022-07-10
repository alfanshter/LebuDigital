package com.lebudigital.lebudigital.webservice

import com.lebudigital.lebudigital.model.auth.PostRegister
import com.lebudigital.lebudigital.model.auth.PostResponse
import com.lebudigital.lebudigital.model.auth.UsersModel
import com.lebudigital.lebudigital.model.beritadesa.BeritaDesaResponse
import com.lebudigital.lebudigital.model.budayalokal.BudayaLokalResponse
import com.lebudigital.lebudigital.model.fasilitas.FasilitasResponse
import com.lebudigital.lebudigital.model.geospasial.GeoSpasialResponse
import com.lebudigital.lebudigital.model.pelatihan.PelatihanResponse
import com.lebudigital.lebudigital.model.profildesa.KegiatanDesaResponse
import com.lebudigital.lebudigital.model.profildesa.ProfilModel
import com.lebudigital.lebudigital.model.profildesa.ProfilResponse
import com.lebudigital.lebudigital.model.tvcc.TvccResponse
import com.lebudigital.lebudigital.model.versi.VersiResponse
import com.lebudigital.lebudigital.model.wilayah.ResponseKecamatan
import com.lebudigital.lebudigital.model.wilayah.ResponseKelurahan
import com.lebudigital.lebudigital.model.wilayah.ResponseKota
import com.lebudigital.lebudigital.model.wilayah.ResponseWilayah
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    //=======================PRODUK==================


    //kurang keranjang
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("password") password: String,
        @Field("province_id") province_id: String,
        @Field("regencie_id") regencie_id: String,
        @Field("district_id") district_id: String,
        @Field("village_id") village_id: String,
        @Field("alamat_lengkap") alamat_lengkap: String,
        @Field("telepon") telepon: String
    ): Call<PostResponse>

    //kurang keranjang
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<PostResponse>

    //=====================Versi=====================
    //versi
    @GET("versi")
    fun getversiaplikasi(
        @Query("nama_aplikasi") nama_aplikasi: String
    ): Call<VersiResponse>
    //=====================END Versi=====================

  //=====================Profil=====================
    //versi
    @GET("profil")
    fun profil(
        @Query("id") id: Int
    ): Call<UsersModel>
    //=====================END Profil=====================

    //=======================TVCC=====================
    //data keranjang sesuai id
    @GET("tvcc")
    fun tvcc(
        @Header("X-Authorization") token: String
    ): Call<TvccResponse>

    @GET("lada")
    fun lada(
        @Header("X-Authorization") token: String
    ): Call<TvccResponse>

    @GET("bayarbeli")
    fun bayarbeli(
        @Header("X-Authorization") token: String
    ): Call<TvccResponse>

    @GET("pasardesa")
    fun pasardesa(
        @Header("X-Authorization") token: String
    ): Call<TvccResponse>
    //=======================END TVCC==================

    //=======================PROFIL DESA=====================
    @GET("profildesa")
    fun profildesa(
        @Header("X-Authorization") token: String,
        @Query("id") id : Int
    ): Call<ProfilResponse>

    //=======================END PROFIL DESA==================

    // =======================FASILITAS DESA=====================
    @GET("fasilitasdesa")
    fun fasilitasdesa(
        @Header("X-Authorization") token: String,
        @Query("id") id : Int
    ): Call<FasilitasResponse>

    //=======================END FASILITAS DESA==================

    //=======================BUDAYA LOKAL==================
    @GET("budayalokal")
    fun budayalokal(
        @Header("X-Authorization") token: String,
        @Query("id") id : Int
    ): Call<BudayaLokalResponse>
    //=======================BUDAYA LOKAL==================

    //=======================GEOSPASIAL==================
    @GET("geospasial")
    fun geospasial(
        @Header("X-Authorization") token: String,
        @Query("id") id : Int
    ): Call<GeoSpasialResponse>
    //=======================END GEOSPASIAL==================

    //=======================Kegiatan Desa==================
    @GET("kegiatandesa")
    fun kegiatandesa(
        @Header("X-Authorization") token: String,
        @Query("id") id : Int
    ): Call<KegiatanDesaResponse>
    //=======================END Kegiatan Desa==================

    //=======================Pelatihan Desa==================
    @GET("pelatihan")
    fun pelatihan(
        @Header("X-Authorization") token: String,
        @Query("id") id : Int
    ): Call<PelatihanResponse>
    //=======================END Pelatihan Desa==================

    //=======================BERITA DESA==================
    @GET("beritadesa")
    fun beritadesa(
        @Header("X-Authorization") token: String,
        @Query("id") id : Int
    ): Call<BeritaDesaResponse>

    @GET("beritadesa_popular")
    fun beritadesa_popular(
        @Header("X-Authorization") token: String,
        @Query("id") id : Int
    ): Call<BeritaDesaResponse>

    @GET("tambah_kunjungan_beritadesa")
    fun tambah_kunjungan_beritadesa(
        @Header("X-Authorization") token: String,
        @Query("id") id : Int
    ): Call<PostResponse>
    //=======================END BERITA DESA==================

    //=====================WILAYAH=====================
    @GET("provinsi")
    fun getprovinsi(): Call<ResponseWilayah>

    @GET("kabupaten")
    fun getkota(
        @Query("province_id") parameter: Int
    ): Call<ResponseKota>

    @GET("kecamatan")
    fun getkecamatan(
        @Query("regency_id") parameter: Int
    ): Call<ResponseKecamatan>

    @GET("desa")
    fun getkelurahan(
        @Query("district_id") parameter: Int
    ): Call<ResponseKelurahan>

    //=====================END WILAYAH=====================
}