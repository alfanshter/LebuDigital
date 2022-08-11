package com.lebudigital.lebudigital.webservice

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object{

        private fun getClient() : Retrofit{
             var retrofit : Retrofit? = null
             val opt = OkHttpClient.Builder().apply {
                connectTimeout(30, TimeUnit.SECONDS)
                readTimeout(30, TimeUnit.SECONDS)
                writeTimeout(30,TimeUnit.SECONDS)
            }.build()

//            return if (retrofit ==null){
                retrofit = Retrofit.Builder().apply {
                    client(opt)
                    baseUrl(Constant.api_backend)
                    addConverterFactory(GsonConverterFactory.create())
                }.build()
                return retrofit!!
//            }else{
//                retrofit!!
//            }
        }

        fun instance() = getClient().create(ApiService::class.java)
    }

}
