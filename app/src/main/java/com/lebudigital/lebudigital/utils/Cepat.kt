package com.lebudigital.lebudigital.utils


import android.content.Context
import android.location.Geocoder
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

object  Cepat {

    fun convert_money(jumlah : Int): String {
        val formatter: NumberFormat = DecimalFormat("#,###")
        val myNumber = jumlah
        val harga: String = formatter.format(myNumber)

        return harga
    }

    //tampilkan kamera A dan B
    fun showbetweenmarker(
        lat: Double,
        lng: Double,
        latitude_tujuan: Double,
        longitude_tujuan: Double,
        namaawal: String,
        namatujuan: String,
        peta : GoogleMap
    ) {
        val marker1 =
            LatLng(java.lang.Double.valueOf(lat), java.lang.Double.valueOf(lng))
        val marker2 = LatLng(latitude_tujuan, longitude_tujuan)

        val markersList: MutableList<Marker> = ArrayList()
        val youMarker: Marker =
            peta.addMarker(MarkerOptions().position(marker1).title(namaawal).visible(false))!!
        val playerMarker: Marker =
            peta.addMarker(MarkerOptions().position(marker2).title(namatujuan).visible(false))!!

        markersList.add(youMarker)
        markersList.add(playerMarker)

        val builder = LatLngBounds.Builder()
        for (m in markersList) {
            builder.include(m.position)
        }
        val padding = 50
        val bounds = builder.build()
        val cu = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        peta.setOnMapLoadedCallback(GoogleMap.OnMapLoadedCallback { //animate camera here
            peta.animateCamera(cu)
        })

    }

    //tampilkan marker
    fun showMarker(lat: Double, lon: Double, msg: String,peta : GoogleMap,bitmapDescriptor: BitmapDescriptor) {
        val coordinate = LatLng(lat, lon)
        peta.addMarker(
            MarkerOptions().position(coordinate).title(msg)
                .icon(bitmapDescriptor)
        )

    }

    //tampilkan alamat
    fun tampilkan_alamat(lat: Double, lon: Double, context: Context): String {
        var name = ""
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(lat, lon, 1)

            if (addresses.size > 0) {
                val fetchedAddress = addresses.get(0)
                val strAddress = StringBuilder()

                for (i in 0..fetchedAddress.maxAddressLineIndex) {
                    name =
                        strAddress.append(fetchedAddress.getAddressLine(i)).append(" ").toString()

                }

            }

        } catch (e: Exception) {
        }
        return name
    }
}