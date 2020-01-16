package com.amary21.e_lapor.network.model

import com.google.gson.annotations.SerializedName

data class Result (
    @SerializedName("id") val id : Int?,
    @SerializedName("nama") val nama : String?,
    @SerializedName("judul") val judul : String?,
    @SerializedName("isi") val isi : String?,
    @SerializedName("foto") val foto : String?,
    @SerializedName("latitude") val latitude : String?,
    @SerializedName("longitude") val longitude : String?,
    @SerializedName("kategori") val kategori : String?,
    @SerializedName("waktu") val waktu : String?,
    @SerializedName("status") val status : String?
)