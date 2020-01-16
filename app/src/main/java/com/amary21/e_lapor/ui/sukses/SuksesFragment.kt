package com.amary21.e_lapor.ui.sukses


import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.amary21.e_lapor.R
import com.amary21.e_lapor.network.ApiClient
import com.amary21.e_lapor.network.model.ResponseRead
import com.amary21.e_lapor.network.model.Result
import kotlinx.android.synthetic.main.fragment_sukses.*
import retrofit2.Call
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class SuksesFragment : Fragment() {

    private lateinit var apiClient: ApiClient
    private var sukseAdapter : SuksesAdapter? = null
    private val listSukses = ArrayList<Result>()

    companion object{
        fun newInstance() = SuksesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sukses, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        apiClient = ApiClient()
        sukseAdapter = SuksesAdapter(context as Activity)

        rvSukses.layoutManager = LinearLayoutManager(context)
        rvSukses.setHasFixedSize(true)
        rvSukses.adapter = sukseAdapter

        val call = apiClient.getClient().readLapor()
        call.enqueue(object : retrofit2.Callback<ResponseRead>{
            override fun onFailure(call: Call<ResponseRead>, t: Throwable) {
                Log.e("Message", t.message.toString())
                Toast.makeText(context, "Message : ${t.message.toString()}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponseRead>, response: Response<ResponseRead>) {
                if (response.body() != null){
                    for (item in response.body()!!.result){
                        if (item.status.equals("sukses")){
                            listSukses.add(
                                Result(
                                    item.id,
                                    item.nama,
                                    item.judul,
                                    item.isi,
                                    item.foto,
                                    item.latitude,
                                    item.longitude,
                                    item.kategori,
                                    item.waktu,
                                    item.status
                                )
                            )
                        }
                    }

                    skSukses.visibility = View.GONE
                    rvSukses.visibility = View.VISIBLE
                    sukseAdapter!!.setResult(listSukses)
                }
            }
        })
    }


}
