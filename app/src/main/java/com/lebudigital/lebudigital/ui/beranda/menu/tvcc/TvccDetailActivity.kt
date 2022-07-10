package com.lebudigital.lebudigital.ui.beranda.menu.tvcc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.databinding.ActivityTvccDetailBinding
import com.lebudigital.lebudigital.model.tvcc.TvccModel
import com.lebudigital.lebudigital.webservice.Constant
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

class TvccDetailActivity : AppCompatActivity() {
    var produkmodel: TvccModel? = null
    lateinit var binding: ActivityTvccDetailBinding

    var menu : String? = null
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
        binding.txtjudul.text = produkmodel!!.judul.toString()
        binding.txtnarasi.text = produkmodel!!.narasi.toString()
        binding.txtlink.text = produkmodel!!.link.toString()

        val sdf = SimpleDateFormat("dd MMM yyyy")
        val currentDate = sdf.format(produkmodel!!.createdAt)

        binding.txttanggal.text = currentDate

        Picasso.get().load(Constant.STORAGE+produkmodel!!.foto).centerCrop().fit().into(binding.imgfoto)


    }
}