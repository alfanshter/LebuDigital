package com.lebudigital.lebudigital.ui.beranda.menu.grubinformasi.fasilitasdesa

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import com.lebudigital.lebudigital.MainActivity
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.databinding.ActivityDetailFasilitasBinding
import com.lebudigital.lebudigital.model.fasilitas.FasilitasModel
import com.lebudigital.lebudigital.webservice.Constant
import com.squareup.picasso.Picasso
import java.io.InputStream
import java.net.URL
import java.net.URLConnection
import java.text.SimpleDateFormat
import java.util.*


class DetailFasilitasActivity : AppCompatActivity(), OnMapReadyCallback {
    var fasilitasmodel: FasilitasModel? = null
    lateinit var binding: ActivityDetailFasilitasBinding

    //lokasi
    var mLocationRequest: LocationRequest? = null
    var mLastLocation: Location? = null
    var mCurrLocationMarker: Marker? = null
    var mFusedLocationClient: FusedLocationProviderClient? = null
    var is_loop = 0

    private lateinit var mMap: GoogleMap

    var menu : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_fasilitas)
        binding.lifecycleOwner = this

        val gson = Gson()
        fasilitasmodel = gson.fromJson(intent.getStringExtra("fasilitas"), FasilitasModel::class.java)
        val bundle: Bundle? = intent.extras
        menu = bundle!!.getString("menu")

        binding.btnback.setOnClickListener {
            finish()
        }

        binding.txtmenu.text = "Fasilitas Desa"
        binding.txtjudul.text = fasilitasmodel!!.fasilitas.toString()
        binding.txtnarasi.text = fasilitasmodel!!.deskripsi.toString()


        val sdf = SimpleDateFormat("dd MMM yyyy")
        val currentDate = sdf.format(Date())

        binding.txttanggal.text = currentDate

        Picasso.get().load(Constant.STORAGE+fasilitasmodel!!.foto).centerCrop().fit().into(binding.imgfoto)

        val mapFragment = supportFragmentManager
            .findFragmentById(com.lebudigital.lebudigital.R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        permission()


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

                MainActivity.latitudePosisi = mLastLocation!!.latitude.toString()
                MainActivity.longitudePosisi = mLastLocation!!.longitude.toString()
                // Add a marker in lokasiku and move the camera
                if (MainActivity.latitudePosisi != null) {
                    val lokasiku = LatLng(
                        MainActivity.latitudePosisi!!.toDouble(),
                        MainActivity.longitudePosisi!!.toDouble()
                    )

                    if (is_loop == 0) {

                        val sydney = LatLng(fasilitasmodel!!.latitude!!.toDouble(), fasilitasmodel!!.longitude!!.toDouble())
                        var bmp  : Bitmap? = null
                        val thread = Thread {
                            try {
                                val imageURLBase = "${Constant.STORAGE+fasilitasmodel!!.icon}"
                                val imageURL = URL(imageURLBase)
                                val connection: URLConnection = imageURL.openConnection()
                                val iconStream: InputStream = connection.getInputStream()
                                bmp = BitmapFactory.decodeStream(iconStream)

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            runOnUiThread {
                                mMap.addMarker(
                                    MarkerOptions().position(sydney)
                                        .title("Lokasi") // below line is use to add custom marker on our map.
                                        .icon(BitmapDescriptorFactory.fromBitmap(bmp!!))
                                )

                            }
                        }
                        thread.start()

                                              mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 18f))

                        mMap.isMyLocationEnabled = true
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
}