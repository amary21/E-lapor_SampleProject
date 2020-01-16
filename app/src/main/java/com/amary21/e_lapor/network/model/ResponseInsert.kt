package com.amary21.e_lapor.network.model

import com.google.gson.annotations.SerializedName

data class ResponseInsert (

    @SerializedName("message")
    var message:String?
)