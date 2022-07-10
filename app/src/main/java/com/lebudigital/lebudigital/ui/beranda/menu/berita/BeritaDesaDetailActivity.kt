package com.lebudigital.lebudigital.ui.beranda.menu.berita

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.databinding.ActivityBeritaDesaDetailBinding
import com.lebudigital.lebudigital.model.auth.PostResponse
import com.lebudigital.lebudigital.model.beritadesa.BeritaDesaModel
import com.lebudigital.lebudigital.model.budayalokal.BudayaLokalModel
import com.lebudigital.lebudigital.webservice.ApiClient
import com.lebudigital.lebudigital.webservice.Constant
import com.squareup.picasso.Picasso
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat


class BeritaDesaDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityBeritaDesaDetailBinding
    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()
    var beritadesa: BeritaDesaModel? = null
    var budayalokal: BudayaLokalModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_berita_desa_detail)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)

        val gson = Gson()
        beritadesa = gson.fromJson(intent.getStringExtra("beritadesa"), BeritaDesaModel::class.java)
        budayalokal = gson.fromJson(intent.getStringExtra("budayalokal"), BudayaLokalModel::class.java)

        if (beritadesa!=null){
            binding.txtjudul.text = beritadesa!!.judul
            binding.txtnarasi.text = beritadesa!!.narasi
            binding.txtmenu.text = "Berita Desa"

            val sdf = SimpleDateFormat("dd MMM yyyy")
            val currentDate = sdf.format(beritadesa!!.tanggalTerbit)

            binding.txttanggal.text = currentDate

            Picasso.get().load(Constant.STORAGE + beritadesa!!.foto).centerCrop().fit()
                .into(binding.imgfoto)

            var link_video = beritadesa!!.video
            var potongan_link = link_video!!.subSequence(0, 5)

            binding.video.setOnClickListener {
                if (potongan_link == "video") {
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(Constant.STORAGE + beritadesa!!.video))
                    startActivity(browserIntent)
                } else {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(beritadesa!!.video))
                    startActivity(browserIntent)

                }

            }

            binding.btnback.setOnClickListener {
                finish()
            }


            tambah_pengunjung()
        }

        if (budayalokal!=null){
            binding.txtjudul.text = budayalokal!!.judul
            binding.txtcagar.text = budayalokal!!.cagarBudaya
            binding.txtnarasi.text = budayalokal!!.uraian
            binding.txtmenu.text = "Budaya Lokal"

            val sdf = SimpleDateFormat("dd MMM yyyy")
            val currentDate = sdf.format(budayalokal!!.tanggalTerbit)

            binding.txttanggal.text = currentDate

            Picasso.get().load(Constant.STORAGE + budayalokal!!.foto).centerCrop().fit()
                .into(binding.imgfoto)

            var link_video = budayalokal!!.video
            var potongan_link = link_video!!.subSequence(0, 5)

            binding.video.setOnClickListener {
                if (potongan_link == "video") {
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(Constant.STORAGE + budayalokal!!.video))
                    startActivity(browserIntent)
                } else {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(budayalokal!!.video))
                    startActivity(browserIntent)

                }

            }

            binding.btnback.setOnClickListener {
                finish()
            }

        }

    }

    fun tambah_pengunjung() {
        api.tambah_kunjungan_beritadesa(Constant.API_KEY_BACKEND, beritadesa!!.id!!)
            .enqueue(object : Callback<PostResponse> {
                override fun onResponse(
                    call: Call<PostResponse>,
                    response: Response<PostResponse>
                ) {

                }

                override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }
}