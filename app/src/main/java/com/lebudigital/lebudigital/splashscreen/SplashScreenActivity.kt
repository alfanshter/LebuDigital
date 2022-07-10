package com.lebudigital.lebudigital.splashscreen

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.lebudigital.lebudigital.BuildConfig
import com.lebudigital.lebudigital.MainActivity
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.auth.LoginActivity
import com.lebudigital.lebudigital.intro.Intro1Activity
import com.lebudigital.lebudigital.model.versi.VersiResponse
import com.lebudigital.lebudigital.session.SessionManager
import com.lebudigital.lebudigital.webservice.ApiClient
import org.jetbrains.anko.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashScreenActivity : AppCompatActivity(),AnkoLogger {
    lateinit var handler: Handler
    lateinit var dialog: AlertDialog
    lateinit var sessionManager: SessionManager

    var api  = ApiClient.instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        sessionManager = SessionManager(this)
      ambildata()

    }

    fun ambildata(){
        val versionName = BuildConfig.VERSION_NAME.toFloat()
        api.getversiaplikasi("lebu").enqueue(object : Callback<VersiResponse> {
            override fun onResponse(call: Call<VersiResponse>, response: Response<VersiResponse>) {
                try {
                    if (response.isSuccessful){
                        val versiaplikasi = response.body()!!.data!!.versiAplikasi
                        if (versionName >= versiaplikasi!!){
                            if (sessionManager.getLogin()==true){
                                handler = Handler()
                                handler.postDelayed({
                                    startActivity(intentFor<MainActivity>().clearTask().newTask())
                                    finish()
                                }, 1500)
                            }else{
                                handler = Handler()
                                handler.postDelayed({
                                    startActivity(intentFor<LoginActivity>().clearTask().newTask())
                                    finish()
                                }, 1500)
                            }

                        }else{
                            showHome()
                        }
                    }else{
                        toast("Kesalahan aplikasi")
                    }
                }catch (e : Exception){
                    info { "dinda ${e.message}" }
                }
            }

            override fun onFailure(call: Call<VersiResponse>, t: Throwable) {
                info { "dinda ${t.message}" }
                toast("Kesalahan Jaringan")
            }

        })

    }


    fun showHome() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Silahkan update dulu aplikasinya ")
        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    val appPackageName =
                        packageName // getPackageName() from Context or Activity object

                    try {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=$appPackageName")
                            )
                        )
                    } catch (anfe: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id==$appPackageName")
                            )
                        )
                    }
                    toast("muask")
                }
                DialogInterface.BUTTON_NEGATIVE -> {
                    showHome()
                }
                DialogInterface.BUTTON_NEUTRAL -> {
                }
            }
        }


        // Set the alert dialog positive/yes button
        builder.setPositiveButton("YES", dialogClickListener)

        // Set the alert dialog negative/no button
        builder.setNegativeButton("NO", dialogClickListener)

        // Set the alert dialog neutral/cancel button
        builder.setNeutralButton("CANCEL", dialogClickListener)


        // Initialize the AlertDialog using builder object
        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()
    }

}