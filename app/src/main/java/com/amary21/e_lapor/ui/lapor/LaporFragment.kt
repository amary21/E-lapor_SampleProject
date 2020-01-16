package com.amary21.e_lapor.ui.lapor


import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.amary21.e_lapor.R
import com.amary21.e_lapor.network.ApiClient
import com.amary21.e_lapor.network.model.ResponseRead
import com.amary21.e_lapor.network.model.Result
import kotlinx.android.synthetic.main.fragment_lapor.*
import retrofit2.Call
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class LaporFragment : Fragment() {

    private lateinit var apiClient: ApiClient
    private var laporAdapter : LaporAdapter? = null
    private val listLapor = ArrayList<Result>()

    companion object{
        fun newInstance() = LaporFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lapor, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        apiClient = ApiClient()
        laporAdapter = LaporAdapter(context as Activity)

        rvLapor.layoutManager = LinearLayoutManager(context)
        rvLapor.setHasFixedSize(true)
        rvLapor.adapter = laporAdapter

        val call = apiClient.getClient().readLapor()
        call.enqueue(object : retrofit2.Callback<ResponseRead>{
            override fun onFailure(call: Call<ResponseRead>, t: Throwable) {
                Log.e("Message", t.message.toString())
                Toast.makeText(context, "Message : ${t.message.toString()}", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponseRead>, response: Response<ResponseRead>) {
                if (response.body() != null){
                    for (item in response.body()!!.result){
                        if (item.status.equals("lapor")){
                            listLapor.add(
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

                    skLapor.visibility = View.GONE
                    rvLapor.visibility = View.VISIBLE
                    laporAdapter!!.setResult(listLapor)
                }
            }
        })

    }
}
