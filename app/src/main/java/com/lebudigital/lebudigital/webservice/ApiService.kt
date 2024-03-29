package com.lebudigital.lebudigital.webservice

import com.lebudigital.lebudigital.model.auth.PostRegister
import com.lebudigital.lebudigital.model.auth.PostResponse
import com.lebudigital.lebudigital.model.auth.UpdataProfilResponse
import com.lebudigital.lebudigital.model.auth.UsersModel
import com.lebudigital.lebudigital.model.banner.BannerResponse
import com.lebudigital.lebudigital.model.beritadesa.BeritaDesaResponse
import com.lebudigital.lebudigital.model.budayalokal.BudayaLokalResponse
import com.lebudigital.lebudigital.model.chatpencarian.PencarianUserResponse
import com.lebudigital.lebudigital.model.desaterdekat.DesaTerdekatResponse
import com.lebudigital.lebudigital.model.fasilitas.FasilitasResponse
import com.lebudigital.lebudigital.model.geospasial.GeoSpasialResponse
import com.lebudigital.lebudigital.model.login.LoginResponse
import com.lebudigital.lebudigital.model.pelatihan.PelatihanResponse
import com.lebudigital.lebudigital.model.profildesa.KegiatanDesaResponse
import com.lebudigital.lebudigital.model.profildesa.ProfilModel
import com.lebudigital.lebudigital.model.profildesa.ProfilResponse
import com.lebudigital.lebudigital.model.tvcc.TvccResponse
import com.lebudigital.lebudigital.model.users.UsersResponse
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
    ): Call<LoginResponse>

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

    //Register

    @Multipart
    @POST("update_profil")
    fun update_profil(
        @Header("X-Authorization") token: String,
        @Part("id") id: RequestBody,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("telepon") telepon: RequestBody,
        @Part("nik") nik: RequestBody,
        @Part("alamat_lengkap") alamat_lengkap: RequestBody
    ): Call<UpdataProfilResponse>

    //FOTO
    @Multipart
    @POST("update_profil")
    fun update_profil_foto_kk_akta_ktp(
        @Header("X-Authorization") token: String,
        @Part("id") id: RequestBody,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("telepon") telepon: RequestBody,
        @Part("nik") nik: RequestBody,
        @Part("alamat_lengkap") alamat_lengkap: RequestBody,
        @Part foto: MultipartBody.Part?,
        @Part foto_kk: MultipartBody.Part?,
        @Part foto_akta: MultipartBody.Part?,
        @Part foto_ktp: MultipartBody.Part?
    ): Call<UpdataProfilResponse>


    //=====================END Profil=====================

    //=======================TVCC=====================
    //data keranjang sesuai id
    @GET("tvcc")
    fun tvcc(
        @Header("X-Authorization") token: String
    ): Call<TvccResponse>

    @GET("search_tvcc")
    fun search_tvcc(
        @Header("X-Authorization") token: String,
        @Query("judul") judul: String
    ): Call<TvccResponse>

    @GET("lada")
    fun lada(
        @Header("X-Authorization") token: String
    ): Call<TvccResponse>

    @GET("search_lada")
    fun search_lada(
        @Header("X-Authorization") token: String,
        @Query("judul") judul: String
    ): Call<TvccResponse>

    @GET("bayarbeli")
    fun bayarbeli(
        @Header("X-Authorization") token: String
    ): Call<TvccResponse>

    @GET("search_bayarbeli")
    fun search_bayarbeli(
        @Header("X-Authorization") token: String,
        @Query("judul") judul: String
    ): Call<TvccResponse>

    @GET("pasardesa")
    fun pasardesa(
        @Header("X-Authorization") token: String
    ): Call<TvccResponse>

    @GET("search_pasardesa")
    fun search_pasardesa(
        @Header("X-Authorization") token: String,
        @Query("judul") judul: String
    ): Call<TvccResponse>

    //=======================END TVCC==================

    //=======================PROFIL DESA=====================
    @GET("profildesa")
    fun profildesa(
        @Header("X-Authorization") token: String,
        @Query("id") id: Int
    ): Call<ProfilResponse>

    //=======================END PROFIL DESA==================

    // =======================FASILITAS DESA=====================
    @GET("fasilitasdesa")
    fun fasilitasdesa(
        @Header("X-Authorization") token: String,
        @Query("id") id: Int
    ): Call<FasilitasResponse>

    @GET("search_fasilitasdesa")
    fun search_fasilitasdesa(
        @Header("X-Authorization") token: String,
        @Query("id") id: String,
        @Query("fasilitas") fasilitas: String
    ): Call<FasilitasResponse>

    //=======================END FASILITAS DESA==================

    //=======================BUDAYA LOKAL==================
    @GET("budayalokal")
    fun budayalokal(
        @Header("X-Authorization") token: String,
        @Query("id") id: Int
    ): Call<BudayaLokalResponse>

    @GET("search_budayalokal")
    fun search_budayalokal(
        @Header("X-Authorization") token: String,
        @Query("id") id: String,
        @Query("judul") judul: String
    ): Call<BudayaLokalResponse>
    //=======================BUDAYA LOKAL==================

    //=======================GEOSPASIAL==================
    @GET("geospasial")
    fun geospasial(
        @Header("X-Authorization") token: String,
        @Query("id") id: Int
    ): Call<GeoSpasialResponse>
    //=======================END GEOSPASIAL==================

    //=======================DESA TERDEKAT==================
    @GET("desa_terdekat")
    fun desa_terdekat(
        @Header("X-Authorization") token: String,
        @Query("id") id: Int,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String
    ): Call<DesaTerdekatResponse>
    //=======================END DESA TERDEKAT==================

    //=======================Kegiatan Desa==================
    @GET("kegiatandesa")
    fun kegiatandesa(
        @Header("X-Authorization") token: String,
        @Query("id") id: Int
    ): Call<KegiatanDesaResponse>

    @GET("search_kegiatandesa")
    fun search_kegiatandesa(
        @Header("X-Authorization") token: String,
        @Query("id") id: String,
        @Query("kegiatan_desa") kegiatan_desa: String
    ): Call<KegiatanDesaResponse>
    //=======================END Kegiatan Desa==================

    //=======================Pelatihan Desa==================
    @GET("pelatihan")
    fun pelatihan(
        @Header("X-Authorization") token: String,
        @Query("id") id: Int
    ): Call<PelatihanResponse>

    @GET("search_pelatihan")
    fun search_pelatihan(
        @Header("X-Authorization") token: String,
        @Query("id") id: String,
        @Query("judul") judul: String
    ): Call<PelatihanResponse>
    //=======================END Pelatihan Desa==================

    //=======================Pelatihan Desa==================
    @GET("profil")
    fun users(
        @Query("id") id: Int
    ): Call<com.lebudigital.lebudigital.model.users.ProfilResponse>
    //=======================END Pelatihan Desa==================

    //=======================BERITA DESA==================
    @GET("beritadesa")
    fun beritadesa(
        @Header("X-Authorization") token: String,
        @Query("id") id: Int
    ): Call<BeritaDesaResponse>

    @GET("beritadesa_popular")
    fun beritadesa_popular(
        @Header("X-Authorization") token: String,
        @Query("id") id: Int
    ): Call<BeritaDesaResponse>

    @GET("tambah_kunjungan_beritadesa")
    fun tambah_kunjungan_beritadesa(
        @Header("X-Authorization") token: String,
        @Query("id") id: Int
    ): Call<PostResponse>

    @GET("search_berita")
    fun search_berita(
        @Header("X-Authorization") token: String,
        @Query("id") id: String,
        @Query("judul") judul: String
    ): Call<BeritaDesaResponse>
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

    //Pencarian User
    @GET("search_user")
    fun search_user(
        @Query("email") email: String
    ): Call<PencarianUserResponse>

    //=====================BANNER=====================
    @GET("get_banner")
    fun get_banner(
        @Header("X-Authorization") token: String,
        @Query("id") id: String): Call<BannerResponse>
    //=====================END BANNER=====================



}