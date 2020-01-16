package com.amary21.e_lapor.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val ID ="com.amary21.e_lapor.ID"
const val NAMA = "com.amary21.e_lapor.NAMA"
const val JUDUL = "com.amary21.e_lapor.JUDUL"
const val ISI = "com.amary21.e_lapor.ISI"
const val FOTO = "com.amary21.e_lapor.FOTO"
const val LATITUDE = "com.amary21.e_lapor.LATITUDE"
const val LONGITUDE = "com.amary21.e_lapor.LONGITUDE"
const val KATEGORI = "com.amary21.e_lapor.KATEGORI"
const val WAKWTU = "com.amary21.e_lapor.WAKTU"
const val STATUS = "com.amary21.e_lapor.STATUS"

class ApiClient {

    companion object{
        const val BASE_URL = "https://e-lapor978.000webhostapp.com/"
    }

    private fun retrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    fun getClient() : ApiInterface{
        return retrofit().create(ApiInterface::class.java)
    }
}