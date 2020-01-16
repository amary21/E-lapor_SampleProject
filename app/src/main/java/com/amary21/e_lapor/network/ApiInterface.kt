package com.amary21.e_lapor.network

import com.amary21.e_lapor.network.model.ResponseInsert
import com.amary21.e_lapor.network.model.ResponseRead
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface ApiInterface {

    @Multipart
    @POST("insert_db.php")
    fun insertLapor(
        @Part image : MultipartBody.Part,
        @PartMap form : Map<String, @JvmSuppressWildcards RequestBody>
    ) : Call<ResponseInsert>

    @POST("read_db.php")
    fun readLapor() :Call<ResponseRead>
}