package com.lebudigital.lebudigital

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.Marker
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lebudigital.lebudigital.ui.ChatFragment
import com.lebudigital.lebudigital.ui.beranda.BerandaFragment
import com.lebudigital.lebudigital.ui.profil.ProfilFragment
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainActivity : AppCompatActivity(),AnkoLogger {
    companion object {
        var latitudePosisi: String? = null
        var longitudePosisi: String? = null
        lateinit var activity : Activity
        const val REQUEST_CHECK_SETTINGS = 101
        private const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000
    }

    lateinit var progressDialog: ProgressDialog

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_beranda -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.framehome,
                        BerandaFragment()
                    ).commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_profil -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.framehome,
                        ProfilFragment()
                    ).commit()
                    return@OnNavigationItemSelectedListener true

                }
                R.id.navigation_chat -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.framehome,
                        ChatFragment()
                    ).commit()
                    return@OnNavigationItemSelectedListener true

                }

            }

            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activity = this
        progressDialog = ProgressDialog(this)

        val navView: BottomNavigationView = findViewById(R.id.nav_viewhome)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        moveToFragment(BerandaFragment())

    }

    private fun moveToFragment(fragment: Fragment) {
        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.framehome, fragment)
        fragmentTrans.commit()
    }

}