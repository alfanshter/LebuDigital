package com.lebudigital.lebudigital.ui.beranda.menu.grubinformasi.geospasial

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.lebudigital.lebudigital.MainActivity.Companion.latitudePosisi
import com.lebudigital.lebudigital.MainActivity.Companion.longitudePosisi
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.adapter.beritadesa.BeritaDesaAdapter
import com.lebudigital.lebudigital.databinding.ActivityBeritaDesaBinding
import com.lebudigital.lebudigital.databinding.ActivityGeospasialBinding
import com.lebudigital.lebudigital.session.SessionManager
import com.lebudigital.lebudigital.utils.Cepat
import com.lebudigital.lebudigital.webservice.ApiClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

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
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)



    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

         // Add a marker in lokasiku and move the camera
        val lokasiku = LatLng(
            latitudePosisi!!.toDouble(),
            longitudePosisi!!.toDouble()
        )


        binding.txtlokasisekarang.text = Cepat.tampilkan_alamat(
            latitudePosisi!!.toDouble(),
            longitudePosisi!!.toDouble(),
            this
        )


        mMap.moveCamera(CameraUpdateFactory.newLatLng(lokasiku))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasiku, 18f))
        googleMap.isMyLocationEnabled = true
    }

}