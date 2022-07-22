package com.lebudigital.lebudigital.ui.beranda.menu.tvcc

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.databinding.ActivityTvccDetailBinding
import com.lebudigital.lebudigital.model.tvcc.TvccModel
import org.jetbrains.anko.AnkoLogger
import java.text.SimpleDateFormat


class TvccDetailActivity : AppCompatActivity(),AnkoLogger {
    var produkmodel: TvccModel? = null
    lateinit var binding: ActivityTvccDetailBinding

    var menu : String? = null
    //video
    private var mediacontroller: MediaController? = null
    private var uri: Uri? = null
    private var isContinuously = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvcc_detail)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tvcc_detail)
        binding.lifecycleOwner = this

        val gson = Gson()
        produkmodel = gson.fromJson(intent.getStringExtra("tvcc"), TvccModel::class.java)
        val bundle: Bundle? = intent.extras
        menu = bundle!!.getString("menu")

        binding.btnback.setOnClickListener {
            finish()
        }

        binding.txtmenu.text = menu
        binding.txtjudul!!.text = produkmodel!!.judul.toString()
        binding.txtnarasi!!.text = produkmodel!!.narasi.toString()
        binding.txtlink!!.text = produkmodel!!.link.toString()

        val sdf = SimpleDateFormat("dd MMM yyyy")
        val currentDate = sdf.format(produkmodel!!.createdAt)

        binding.txttanggal!!.text = currentDate

        playvideo()

    }
    fun playvideo(){
        binding.imgfoto.loadUrl(produkmodel!!.link!!)
    }

    override fun onBackPressed() {
        if (binding.imgfoto.canGoBack()){
            binding.imgfoto.goBack()
        }else{
            super.onBackPressed()

        }
    }

}