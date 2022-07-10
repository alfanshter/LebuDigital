package com.lebudigital.lebudigital.ui.beranda.menu.grubberita.kegiatandesa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.databinding.ActivityKegiatanDesaDetailBinding
import com.lebudigital.lebudigital.model.kegiatandesa.KegiatanDesaModel
import java.text.SimpleDateFormat

class KegiatanDesaDetailActivity : AppCompatActivity() {
    var kegiatandesa: KegiatanDesaModel? = null
    lateinit var binding: ActivityKegiatanDesaDetailBinding

    var menu : String? = null
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




    }
}