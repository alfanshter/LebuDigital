package com.lebudigital.lebudigital.ui.beranda.menu.grubinformasi.profildesa

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.adapter.profildesa.DataStatistikAdapter
import com.lebudigital.lebudigital.adapter.profildesa.GambarDesaSlider
import com.lebudigital.lebudigital.databinding.ActivityProfilDesaBinding
import com.lebudigital.lebudigital.model.profildesa.DataStatistikModel
import com.lebudigital.lebudigital.model.profildesa.GambarDesaModel
import com.lebudigital.lebudigital.model.profildesa.ProfilResponse
import com.lebudigital.lebudigital.session.SessionManager
import com.lebudigital.lebudigital.webservice.ApiClient
import com.lebudigital.lebudigital.webservice.Constant
import com.smarteist.autoimageslider.SliderView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilDesaActivity : AppCompatActivity(),AnkoLogger {
    lateinit var binding: ActivityProfilDesaBinding
    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()
    lateinit var sessionManager: SessionManager
    private lateinit var mAdapter: DataStatistikAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profil_desa)
        binding.lifecycleOwner = this
        binding.btnback.setOnClickListener {
            finish()
        }
        progressDialog = ProgressDialog(this)
        sessionManager = SessionManager(this)
    }

    override fun onStart() {
        super.onStart()
        data_statistik()
    }


    fun data_statistik() {
        //gambar desa
        var gambarmodel = mutableListOf<GambarDesaModel>()

        //datastatistik
        binding.rvdatastatistik.layoutManager = LinearLayoutManager(this)
        binding.rvdatastatistik.setHasFixedSize(true)
        (binding.rvdatastatistik.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL

        api.profildesa(Constant.API_KEY_BACKEND,sessionManager.getiduser()!!)
            .enqueue(object : Callback<ProfilResponse> {
                override fun onResponse(
                    call: Call<ProfilResponse>,
                    response: Response<ProfilResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<DataStatistikModel>()
                            val data = response.body()
                            if (data!!.dataStatistik!!.isEmpty()) {

                            } else {

                                binding.txtkecamatan.text = response.body()!!.profil!!.provinsi!!.name
                                binding.txtdesa.text = response.body()!!.profil!!.desa!!.name
                                binding.txtketerangan.text = response.body()!!.profil!!.deskripsi
                                binding.txtalamat.text = response.body()!!.profil!!.alamat
                                binding.txtnamakepaladesa.text = response.body()!!.profil!!.kepalaDesa
                                binding.txtsekretarisdesa.text = response.body()!!.profil!!.sekretarisDesa
                                binding.txtnamadesa.text = response.body()!!.profil!!.desa!!.name
                                for (hasil in data.dataStatistik!!) {

                                    notesList.add(hasil)
                                    mAdapter = DataStatistikAdapter(notesList)
                                    binding.rvdatastatistik.adapter = mAdapter

                                    mAdapter.notifyDataSetChanged()
                                }

                                val imageList: ArrayList<String> = ArrayList()
                                
                                for (gambardesa in data.gambarDesa!!){
                                    gambarmodel.add(gambardesa)
                                    //slider
                                    imageList.add(Constant.STORAGE + gambardesa.foto!!)

                                }
                                setImageInSlider(imageList, binding.imageSlider, gambarmodel)

                            }

                        } else {
                            toast("gagal mendapatkan response")
                        }
                    } catch (e: Exception) {
                        info { "dinda ${e.message}" }
                    }
                }

                override fun onFailure(call: Call<ProfilResponse>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })

    }

    private fun setImageInSlider(
        images: ArrayList<String>,
        imageSlider: SliderView,
        gambardesa: MutableList<GambarDesaModel>
    ) {
        val adapter = GambarDesaSlider()
     
        adapter.renewItems(images, gambardesa)
        binding.imageSlider.setSliderAdapter(adapter)
        binding.imageSlider.isAutoCycle = true
        binding.imageSlider.scrollTimeInSec = 3
        binding.imageSlider.startAutoCycle()
    }

}