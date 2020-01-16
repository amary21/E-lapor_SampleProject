package com.amary21.e_lapor.network.model

import com.google.gson.annotations.SerializedName

data class ResponseRead (
    @SerializedName("result") val result : List<Result>
)