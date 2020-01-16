package com.amary21.e_lapor.utils

import java.text.SimpleDateFormat
import java.util.*

class DateConvert {

    fun convertDate(dateEvent: String): String {
        var date = dateEvent
        try {
            var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
            val convertDate = simpleDateFormat.parse(date)
            simpleDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.US)
            date = simpleDateFormat.format(convertDate!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return date
    }


}