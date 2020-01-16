package com.amary21.e_lapor.utils

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.amary21.e_lapor.DetailActivity
import com.amary21.e_lapor.network.*
import com.amary21.e_lapor.network.model.Result
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_list.view.*

class ItemViewAdapter(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(item: Result, context: Context) {
        itemView.tvNamaRead.text = item.nama
        itemView.tvJudulRead.text = item.judul
        Glide.with(itemView.context)
            .load(item.foto)
            .into(itemView.imgPhotoRead)
        itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(ID, item.id)
            intent.putExtra(NAMA, item.nama)
            intent.putExtra(ISI, item.isi)
            intent.putExtra(JUDUL, item.judul)
            intent.putExtra(FOTO, item.foto)
            intent.putExtra(LATITUDE, item.latitude)
            intent.putExtra(LONGITUDE, item.longitude)
            intent.putExtra(KATEGORI, item.kategori)
            intent.putExtra(WAKWTU, item.waktu)
            intent.putExtra(STATUS, item.status)
            context.startActivity(intent)
        }
    }

}
