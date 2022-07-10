package com.lebudigital.lebudigital.ui.beranda.menu.grubinformasi.fasilitasdesa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.databinding.ActivityDetailFasilitasBinding
import com.lebudigital.lebudigital.model.fasilitas.FasilitasModel
import com.lebudigital.lebudigital.webservice.Constant
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class DetailFasilitasActivity : AppCompatActivity() {
    var produkmodel: FasilitasModel? = null
    lateinit var binding: ActivityDetailFasilitasBinding

    var menu : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_fasilitas)
        binding.lifecycleOwner = this

        val gson = Gson()
        produkmodel = gson.fromJson(intent.getStringExtra("fasilitas"), FasilitasModel::class.java)
        val bundle: Bundle? = intent.extras
        menu = bundle!!.getString("menu")

        binding.btnback.setOnClickListener {
            finish()
        }

        binding.txtmenu.text = "Fasilitas Desa"
        binding.txtjudul.text = produkmodel!!.fasilitas.toString()
        binding.txtnarasi.text = produkmodel!!.deskripsi.toString()


        val sdf = SimpleDateFormat("dd MMM yyyy")
        val currentDate = sdf.format(Date())

        binding.txttanggal.text = currentDate

        Picasso.get().load(Constant.STORAGE+produkmodel!!.foto).centerCrop().fit().into(binding.imgfoto)


    }
}