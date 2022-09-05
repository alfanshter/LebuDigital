package com.lebudigital.lebudigital.ui.beranda.banner

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.databinding.ActivityBannerBinding
import com.lebudigital.lebudigital.model.banner.BannerModel
import com.lebudigital.lebudigital.webservice.ApiClient
import com.lebudigital.lebudigital.webservice.Constant
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

class BannerActivity : AppCompatActivity() {
    lateinit var binding: ActivityBannerBinding
    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()
    var banner: BannerModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_banner)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)

        val gson = Gson()
        banner = gson.fromJson(intent.getStringExtra("banner"), BannerModel::class.java)

        if (banner!=null){
            binding.txtjudul.text = banner!!.judul
            binding.txtnarasi.text = banner!!.deskripsi
            binding.txtmenu.text = "Banner"

            val sdf = SimpleDateFormat("dd MMM yyyy")
            val currentDate = sdf.format(banner!!.createdAt)
            binding.txttanggal.text = currentDate

            Picasso.get().load(Constant.STORAGE + banner!!.foto).centerCrop().fit()
                .into(binding.imgfoto)

            var link_video = banner!!.link
            var potongan_link = link_video!!.subSequence(0, 5)

            binding.link.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(banner!!.link))
                startActivity(browserIntent)
            }

            binding.btnback.setOnClickListener {
                finish()
            }



        }


    }


}