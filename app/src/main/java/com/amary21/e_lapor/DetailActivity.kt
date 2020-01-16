package com.amary21.e_lapor

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amary21.e_lapor.network.*
import com.amary21.e_lapor.utils.DateConvert
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val id = intent.getIntExtra(ID, 1)
        val nama = intent.getStringExtra(NAMA)
        val judul = intent.getStringExtra(JUDUL)
        val isi = intent.getStringExtra(ISI)
        val foto = intent.getStringExtra(FOTO)
        val latitude = intent.getStringExtra(LATITUDE)
        val longitude = intent.getStringExtra(LONGITUDE)
        val kategori = intent.getStringExtra(KATEGORI)
        val waktu = intent.getStringExtra(WAKWTU)
        val status = intent.getStringExtra(STATUS)
        val dateConvert = DateConvert()

        tvNameDetail.text = nama
        tvJudulDetail.text = judul
        tvIsiDetail.text = isi
        tvAlamatDetail.text = "$latitude / $longitude"
        tvKategeoriDetail.text = kategori
        tvDateDetail.text = dateConvert.convertDate(waktu)
        tvStatusDetail.text = status

        Glide.with(this)
            .load(foto)
            .into(imgDetail)
    }
}
