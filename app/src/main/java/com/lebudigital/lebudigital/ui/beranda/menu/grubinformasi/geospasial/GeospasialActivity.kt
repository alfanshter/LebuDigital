package com.lebudigital.lebudigital.ui.beranda.menu.grubinformasi.geospasial

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lebudigital.lebudigital.MainActivity.Companion.latitudePosisi
import com.lebudigital.lebudigital.MainActivity.Companion.longitudePosisi
import com.lebudigital.lebudigital.adapter.beritadesa.BeritaDesaAdapter
import com.lebudigital.lebudigital.databinding.ActivityGeospasialBinding
import com.lebudigital.lebudigital.model.geospasial.GeoSpasialResponse
import com.lebudigital.lebudigital.session.SessionManager
import com.lebudigital.lebudigital.utils.Cepat
import com.lebudigital.lebudigital.webservice.ApiClient
import com.lebudigital.lebudigital.webservice.Constant
import org.jetbrains.anko.AnkoLogger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.lebudigital.lebudigital.R
import org.jetbrains.anko.info

class GeospasialActivity : AppCompatActivity(), OnMapReadyCallback ,AnkoLogger{


    lateinit var binding: ActivityGeospasialBinding
    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()
    private lateinit var mAdapter: BeritaDesaAdapter
    private lateinit var popularAdapter: BeritaDesaAdapter
    lateinit var sessionManager: SessionManager
    private lateinit var mMap: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_geospasial)
        binding.lifecycleOwner = this
        sessionManager = SessionManager(this)
        progressDialog = ProgressDialog(this)

        binding.btnback.setOnClickListener {
            finish()
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        val mapFragment = supportFragmentManager
            .findFragmentById(com.lebudigital.lebudigital.R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)



    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        api.geospasial(Constant.API_KEY_BACKEND,sessionManager.getiduser()!!).enqueue(object : Callback<GeoSpasialResponse>{
            override fun onResponse(
                call: Call<GeoSpasialResponse>,
                response: Response<GeoSpasialResponse>
            ) {
                if (response.isSuccessful){
                    // Add a marker in lokasiku and move the camera
                    val sydney = LatLng(response.body()!!.desa!!.latitude!!, response.body()!!.desa!!.longitude!!)
                    mMap.addMarker(
                        MarkerOptions().position(sydney)
                            .title(response.body()!!.desa!!.desa!!.name) // below line is use to add custom marker on our map.
                            .icon(BitmapFromVector(applicationContext, com.lebudigital.lebudigital.R.drawable.imgmarker))
                    )
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 18f))

                    if (latitudePosisi!=null ){
                        val lokasiku = LatLng(
                            latitudePosisi!!.toDouble(),
                            longitudePosisi!!.toDouble()
                        )

                        binding.txtlokasisekarang.text = Cepat.tampilkan_alamat(
                            latitudePosisi!!.toDouble(),
                            longitudePosisi!!.toDouble(),
                            this@GeospasialActivity
                        )



                    }
                }else{
                    info { "dinda kesalahan response" }
                }
            }

            override fun onFailure(call: Call<GeoSpasialResponse>, t: Throwable) {
                info { "dinda ${t.message}" }
            }

        })


        googleMap.isMyLocationEnabled = true
    }

    private fun BitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        // below line is use to generate a drawable.
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)

        // below line is use to set bounds to our vector drawable.
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )

        // below line is use to create a bitmap for our
        // drawable which we have added.
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        // below line is use to add bitmap in our canvas.
        val canvas = Canvas(bitmap)

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas)

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

}