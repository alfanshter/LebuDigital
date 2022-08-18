package com.lebudigital.lebudigital.ui.beranda.menu.grubinformasi.desaterdekat

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import com.lebudigital.lebudigital.MainActivity
import com.lebudigital.lebudigital.MainActivity.Companion.latitudePosisi
import com.lebudigital.lebudigital.MainActivity.Companion.longitudePosisi
import com.lebudigital.lebudigital.databinding.ActivityGeospasialBinding
import com.lebudigital.lebudigital.session.SessionManager
import com.lebudigital.lebudigital.utils.Cepat
import com.lebudigital.lebudigital.webservice.ApiClient
import com.lebudigital.lebudigital.webservice.Constant
import org.jetbrains.anko.AnkoLogger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.adapter.desaterdekat.DesaTerdekatAdapter
import com.lebudigital.lebudigital.databinding.ActivityDesaTerdekatBinding
import com.lebudigital.lebudigital.model.budayalokal.BudayaLokalModel
import com.lebudigital.lebudigital.model.desaterdekat.DesaTerdekatModel
import com.lebudigital.lebudigital.model.desaterdekat.DesaTerdekatResponse
import com.lebudigital.lebudigital.ui.beranda.menu.berita.BeritaDesaDetailActivity
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity

class DesaTerdekatActivity : AppCompatActivity(), OnMapReadyCallback, AnkoLogger {


    lateinit var binding: ActivityDesaTerdekatBinding
    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()
    private lateinit var mAdapter: DesaTerdekatAdapter
    lateinit var sessionManager: SessionManager
    private lateinit var mMap: GoogleMap

    //lokasi
    var mLocationRequest: LocationRequest? = null
    var mLastLocation: Location? = null
    var mCurrLocationMarker: Marker? = null
    var mFusedLocationClient: FusedLocationProviderClient? = null

    var is_loop = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_desa_terdekat)
        binding.lifecycleOwner = this
        sessionManager = SessionManager(this)
        progressDialog = ProgressDialog(this)



        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        loading(true)
        binding.btnback.setOnClickListener {
            finish()
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        permission()

        binding.rvdesaterdekat.layoutManager = LinearLayoutManager(this)
        binding.rvdesaterdekat.setHasFixedSize(true)
        (binding.rvdesaterdekat.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL



    }

    //lokasi
    @SuppressLint("MissingPermission")
    private fun permission() {
        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = 20000 // two minute interval

        mLocationRequest!!.fastestInterval = 10000
        mLocationRequest!!.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                //Location Permission already granted
                mFusedLocationClient!!.requestLocationUpdates(
                    mLocationRequest!!, mLocationCallback,
                    Looper.myLooper()
                )
            } else {
                //Request Location Permission
                checkLocationPermission()
            }
        } else {

            mFusedLocationClient!!.requestLocationUpdates(
                mLocationRequest!!, mLocationCallback,
                Looper.myLooper()
            )
        }
    }


    var mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val locationList = locationResult.locations
            if (locationList.size > 0) {
                //The last location in the list is the newest
                val location = locationList[locationList.size - 1]
                mLastLocation = location

                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker!!.remove()
                }

                latitudePosisi = mLastLocation!!.latitude.toString()
                longitudePosisi = mLastLocation!!.longitude.toString()
                // Add a marker in lokasiku and move the camera
                if (latitudePosisi != null) {
                    loading(false)
                    val lokasiku = LatLng(
                        MainActivity.latitudePosisi!!.toDouble(),
                        MainActivity.longitudePosisi!!.toDouble()
                    )

                    if (is_loop == 0) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(lokasiku))
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasiku, 15f))

                        is_loop =1
                        api.desa_terdekat(
                            Constant.API_KEY_BACKEND,
                            sessionManager.getiduser()!!,
                            latitudePosisi!!,
                            longitudePosisi!!
                        ).enqueue(object : Callback<DesaTerdekatResponse> {
                            override fun onResponse(
                                call: Call<DesaTerdekatResponse>,
                                response: Response<DesaTerdekatResponse>
                            ) {
                                if (response.isSuccessful) {
                                    // Add a marker in lokasiku and move the camera
                                    val notesList = mutableListOf<DesaTerdekatModel>()
                                    val data = response.body()!!
                                    info { "dinda desa $data" }

                                    for (hasil in data.data!!) {
                                        if (latitudePosisi != null) {
                                            val sydney = LatLng(hasil.latitude!!, hasil.longitude!!)

                                            val lokasiku = LatLng(
                                                latitudePosisi!!.toDouble(),
                                                longitudePosisi!!.toDouble()
                                            )

                                            binding.txtlokasisekarang.text = Cepat.tampilkan_alamat(
                                                latitudePosisi!!.toDouble(),
                                                longitudePosisi!!.toDouble(),
                                                this@DesaTerdekatActivity
                                            )

                                            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                                            mMap.animateCamera(
                                                CameraUpdateFactory.newLatLngZoom(
                                                    lokasiku,
                                                    18f
                                                )
                                            )

                                            mMap.addMarker(
                                                MarkerOptions().position(sydney)
                                                    .title(hasil.desa!!.name) // below line is use to add custom marker on our map.
                                                    .icon(
                                                        BitmapFromVector(
                                                            applicationContext,
                                                            com.lebudigital.lebudigital.R.drawable.imgmarker
                                                        )
                                                    )
                                            )


                                        }

                                    }

                                    if (data.data.isEmpty()) {
                                        binding.shimmermakanan.stopShimmer()
                                        binding.shimmermakanan.visibility = View.GONE
                                        binding.txtnodata.visibility = View.VISIBLE
                                        binding.rvdesaterdekat.visibility = View.GONE
                                    } else {
                                        binding.shimmermakanan.stopShimmer()
                                        binding.shimmermakanan.visibility = View.GONE
                                        binding.txtnodata.visibility = View.GONE
                                        binding.rvdesaterdekat.visibility = View.VISIBLE


                                        for (hasil in data.data) {
                                            notesList.add(hasil)
                                            mAdapter = DesaTerdekatAdapter(notesList)
                                            binding.rvdesaterdekat.adapter = mAdapter

                                            mAdapter.setDialog(object : DesaTerdekatAdapter.Dialog {


                                                override fun onClick(
                                                    position: Int,
                                                    DesaTerdekatModel: DesaTerdekatModel
                                                ) {
                                                    val gson = Gson()
                                                    val noteJson = gson.toJson(DesaTerdekatModel)
                                                    startActivity<BeritaDesaDetailActivity>(
                                                        "budayalokal" to noteJson
                                                    )
                                                }


                                            })


                                            mAdapter.notifyDataSetChanged()
                                        }


                                    }


                                } else {

                                }
                            }

                            override fun onFailure(call: Call<DesaTerdekatResponse>, t: Throwable) {
                                info { "dinda ${t.message}" }
                            }

                        })

                    }
                }

            }
        }
    }

    val MY_PERMISSIONS_REQUEST_LOCATION = 99
    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {

                // Show an explanation to the user *asynchronously* -- don't block
                // requireActivity() thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(this)
                    .setTitle("Location Permission Needed")
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton(
                        "OK"
                    ) { dialogInterface, i -> //Prompt the user once explanation has been shown
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            MY_PERMISSIONS_REQUEST_LOCATION
                        )
                    }
                    .create()
                    .show()
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        mFusedLocationClient!!.requestLocationUpdates(
                            mLocationRequest!!,
                            mLocationCallback, Looper.myLooper()
                        )
                    }
                } else {
                    // if not allow a permission, the application will exit
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_HOME)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    this.finish()
                    System.exit(0)
                }
            }
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


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

    fun loading(state: Boolean) {
        if (state) {
            progressDialog.setTitle("Sedang Mencari Lokasi")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

        } else {
            progressDialog.dismiss()

        }
    }

}