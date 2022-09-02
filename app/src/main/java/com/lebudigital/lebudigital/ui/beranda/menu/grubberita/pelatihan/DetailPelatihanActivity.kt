package com.lebudigital.lebudigital.ui.beranda.menu.grubberita.pelatihan

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.databinding.ActivityDetailPelatihanBinding
import com.lebudigital.lebudigital.databinding.ActivityTvccDetailBinding
import com.lebudigital.lebudigital.model.pelatihan.PelatihanMinggu
import com.lebudigital.lebudigital.webservice.Constant
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat


class DetailPelatihanActivity : AppCompatActivity() {
    var pelatihan: PelatihanMinggu? = null
    lateinit var binding: ActivityDetailPelatihanBinding

    var menu : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_pelatihan)
        binding.lifecycleOwner = this

        val gson = Gson()
        pelatihan = gson.fromJson(intent.getStringExtra("pelatihan"), PelatihanMinggu::class.java)
        val bundle: Bundle? = intent.extras

        binding.btnback.setOnClickListener {
            finish()
        }

        binding.txtjudul.text = pelatihan!!.judul.toString()

        binding.txtnarasumber.text = pelatihan!!.naraSumber.toString()
        binding.txtkontak.text = pelatihan!!.kontakPerson.toString()
        binding.txtlink.text = pelatihan!!.linkPendaftaran.toString()
        binding.txtlink.setOnClickListener {
            val url = pelatihan!!.linkPendaftaran
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)

        }
        binding.txtdeskripsi.text = pelatihan!!.deskripsi.toString()
        binding.txtalamat.text = pelatihan!!.lokasiKegiatan.toString()

        val sdf = SimpleDateFormat("dd MMM yyyy")
        val currentDate = sdf.format(pelatihan!!.tanggal)

        binding.txttanggal.text = currentDate

        Picasso.get().load(Constant.STORAGE+pelatihan!!.foto).centerCrop().fit().into(binding.imgfoto)

        var link_video = pelatihan!!.video
        var potongan_link = link_video!!.subSequence(0, 5)

        binding.imgvideo.setOnClickListener {
            if (potongan_link == "video") {
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(Constant.STORAGE + pelatihan!!.video))
                startActivity(browserIntent)
            } else {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(pelatihan!!.video))
                startActivity(browserIntent)

            }

        }

    }
}