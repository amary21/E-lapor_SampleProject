package com.amary21.e_lapor.ui.lapor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amary21.e_lapor.R
import com.amary21.e_lapor.network.model.Result
import com.amary21.e_lapor.utils.ItemViewAdapter

class LaporAdapter(private val context: Context) :RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var result : ArrayList<Result> = ArrayList()

    fun  setResult(result: List<Result>){
        this.result.addAll(result)
        notifyDataSetChanged()
    }

    private fun getItem(position: Int) :Result{
        return result[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ItemViewAdapter(view)
    }

    override fun getItemCount(): Int {
        return result.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewAdapter).bind(getItem(position), context)
    }
}