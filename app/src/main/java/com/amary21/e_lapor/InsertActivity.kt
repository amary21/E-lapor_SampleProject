@file:Suppress("DEPRECATION")

package com.amary21.e_lapor

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.text.Editable
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.amary21.e_lapor.network.ApiClient
import com.amary21.e_lapor.network.model.ResponseInsert
import com.bumptech.glide.Glide
import com.google.android.gms.location.*
import com.vincent.filepicker.Constant.*
import com.vincent.filepicker.activity.ImagePickActivity
import com.vincent.filepicker.filter.entity.ImageFile
import kotlinx.android.synthetic.main.activity_insert.*
import kotlinx.android.synthetic.main.content_insert.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Response
import java.io.File


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class InsertActivity : AppCompatActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    var latitude: String? = null
    var longitude: String? = null
    private var image : MultipartBody.Part? = null
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)
        toolbar.title = ""
        setSupportActionBar(toolbar)
        Glide.with(this)
            .asGif()
            .load(R.drawable.scroll_down)
            .into(fabScrollDown)

        apiClient = ApiClient()

        fabHome.setOnClickListener {
            val intent = Intent(this, ReadActivity::class.java)
            startActivity(intent)
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )
        } else {
            buildLocationRequest()
            buildLocationCallback(this)

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_CODE
                )
            }

            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }

        btnInsertForm.setOnClickListener {
            tvTitleCreate.visibility = View.VISIBLE
            inputNama.visibility = View.VISIBLE
            inputJudul.visibility = View.VISIBLE
            inputIsi.visibility = View.VISIBLE
            inputKategori.visibility = View.VISIBLE
            inputLatLong.visibility = View.VISIBLE
            inputFoto.visibility = View.VISIBLE
            tvErrorInput.visibility = View.VISIBLE
            btnProsesInsert.visibility = View.VISIBLE

            edtNama.text = null
            edtJudul.text = null
            edtIsi.text = null
            edtKategori.text = null
            imgAddFoto.visibility = View.VISIBLE
            imgFoto.visibility = View.GONE

            imgMessage.visibility = View.GONE
            tvMessage.visibility = View.GONE
            btnInsertForm.visibility = View.GONE
        }

    }

    private fun buildLocationCallback(context: InsertActivity) {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                val location = p0?.locations?.get(p0.locations.size - 1)
                if (location != null) {
                    latitude = location.latitude.toString()
                    longitude = location.longitude.toString()

                    edtLongitude.setText(longitude)
                    edtLatitude.setText(latitude)

                    edtKategori.setOnClickListener {
                        PopupMenu(context, edtKategori).apply {
                            menuInflater.inflate(R.menu.menu_kategori, menu)
                            setOnMenuItemClickListener {
                                edtKategori.setText(it.title)
                                true
                            }
                            show()
                        }
                    }

                    imgAddFoto.setOnClickListener {
                        if (EasyPermissions.hasPermissions(this@InsertActivity, Manifest.permission.READ_EXTERNAL_STORAGE)){
                            val intent = Intent(this@InsertActivity, ImagePickActivity::class.java)
                            intent.putExtra(MAX_NUMBER, 1)
                            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
                        }else{
                            EasyPermissions.requestPermissions(this@InsertActivity,"This application need your permission to access photo gallery.",991,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                    }


                    imgFoto.setOnClickListener {
                        if (EasyPermissions.hasPermissions(this@InsertActivity, Manifest.permission.READ_EXTERNAL_STORAGE)){
                            val intent = Intent(this@InsertActivity, ImagePickActivity::class.java)
                            intent.putExtra(MAX_NUMBER, 1)
                            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
                        }else{
                            EasyPermissions.requestPermissions(this@InsertActivity,"This application need your permission to access photo gallery.",991,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                    }

                    btnProsesInsert.setOnClickListener {
                        when {
                            edtNama.text.isEmpty() -> {
                                edtNama.error = "Nama masih kosong"
                                tvErrorInput.text = getString(R.string.error_input)
                            }
                            edtJudul.text.isEmpty() -> {
                                edtJudul.error = "Judul masih kosong"
                                tvErrorInput.text = getString(R.string.error_input)
                            }
                            edtIsi.text.isEmpty() -> {
                                edtIsi.error = "Isi masih kosong"
                                tvErrorInput.text = getString(R.string.error_input)
                            }
                            edtKategori.text.isEmpty() -> {
                                edtKategori.error = "Kategori masih kosong"
                                tvErrorInput.text = getString(R.string.error_input)
                            }
                            edtLatitude.text.isEmpty() -> {
                                edtLatitude.error = "Latitude masih kosong"
                                tvErrorInput.text = getString(R.string.error_input)
                            }
                            edtLongitude.text.isEmpty() -> {
                                edtLongitude.error = "Longitude masih error"
                                tvErrorInput.text = getString(R.string.error_input)
                            }
                            else -> {
                                tvErrorInput.text = ""
                                uploadDB(edtLatitude.text, edtLongitude.text)
                            }
                        }

                    }
                }
            }
        }
    }

    private fun uploadDB(latitude: Editable, longitude: Editable) {
        val loading = ProgressDialog(this)
        loading.setMessage("Please wait...")
        loading.show()

        val map : HashMap<String, RequestBody> = HashMap()
        map["nama"] = createFormString(edtNama.text.toString())
        map["judul"] = createFormString(edtJudul.text.toString())
        map["isi"] = createFormString(edtIsi.text.toString())
        map["latitude"] = createFormString(latitude.toString())
        map["longitude"] = createFormString(longitude.toString())
        map["kategori"] = createFormString(edtKategori.text.toString())

        val call = apiClient.getClient().insertLapor(image!!, map)
        call.enqueue(object : retrofit2.Callback<ResponseInsert>{
            override fun onFailure(call: Call<ResponseInsert>, t: Throwable) {
                loading.dismiss()
                Toast.makeText(this@InsertActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<ResponseInsert>,
                response: Response<ResponseInsert>
            ) {

                loading.dismiss()
                if (response.body()?.message == "data succesfull added"){
                    tvTitleCreate.visibility = View.GONE
                    inputNama.visibility = View.GONE
                    inputJudul.visibility = View.GONE
                    inputIsi.visibility = View.GONE
                    inputKategori.visibility = View.GONE
                    inputLatLong.visibility = View.GONE
                    inputFoto.visibility = View.GONE
                    tvErrorInput.visibility = View.GONE
                    btnProsesInsert.visibility = View.GONE

                    imgMessage.visibility = View.VISIBLE
                    tvMessage.text = response.body()?.message
                    tvMessage.visibility = View.VISIBLE
                    btnInsertForm.visibility = View.VISIBLE

                }else{
                    tvErrorInput.text = getString(R.string.error_input)
                }
            }

        })




    }

    private fun createFormString(description: String): RequestBody {
        return description
            .toRequestBody(MultipartBody.FORM)
    }


    private fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement = 10f
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null){
            imgAddFoto.visibility = View.GONE
            imgFoto.visibility = View.VISIBLE

            val pickedImage = data.getParcelableArrayListExtra<ImageFile>(RESULT_PICK_IMAGE)[0]?.path

            val requestBody = File(pickedImage).asRequestBody("multipart".toMediaTypeOrNull())
            image = MultipartBody.Part.createFormData("image", File(pickedImage).name, requestBody)

            Glide.with(this).load(pickedImage).into(imgFoto)
        }
    }


    companion object {
        const val REQUEST_CODE = 256
    }
}
