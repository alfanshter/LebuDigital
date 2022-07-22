package com.lebudigital.lebudigital.ui.beranda.menu.grubberita.kegiatandesa

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.databinding.ActivityKegiatanDesaDetailBinding
import com.lebudigital.lebudigital.model.kegiatandesa.KegiatanDesaModel
import java.text.SimpleDateFormat

class KegiatanDesaDetailActivity : AppCompatActivity(), OnMapReadyCallback {
    var kegiatandesa: KegiatanDesaModel? = null
    lateinit var binding: ActivityKegiatanDesaDetailBinding

    var menu : String? = null
    private lateinit var mMap: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_kegiatan_desa_detail)
        binding.lifecycleOwner = this

        val gson = Gson()
        kegiatandesa = gson.fromJson(intent.getStringExtra("kegiatandesa"), KegiatanDesaModel::class.java)
        val bundle: Bundle? = intent.extras

        binding.btnback.setOnClickListener {
            finish()
        }
        val sdf = SimpleDateFormat("dd MMM yyyy")
        val currentDate = sdf.format(kegiatandesa!!.tanggal)

        binding.txtjudul.text = kegiatandesa!!.namaKegiatan.toString()
        binding.txtnamadesa.text = kegiatandesa!!.kegiatanDesa.toString()
        binding.txtalaamt.text = kegiatandesa!!.alamat.toString()
        binding.txtjam.text = kegiatandesa!!.jam.toString()
        binding.txttanggal.text = currentDate

        val mapFragment = supportFragmentManager
            .findFragmentById(com.lebudigital.lebudigital.R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val sydney = LatLng(kegiatandesa!!.latitude!!.toDouble(), kegiatandesa!!.longitude!!.toDouble())
        mMap.addMarker(
            MarkerOptions().position(sydney)
                .title("Lokasi") // below line is use to add custom marker on our map.
                .icon(BitmapFromVector(applicationContext, com.lebudigital.lebudigital.R.drawable.imgmarker))
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 18f))

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