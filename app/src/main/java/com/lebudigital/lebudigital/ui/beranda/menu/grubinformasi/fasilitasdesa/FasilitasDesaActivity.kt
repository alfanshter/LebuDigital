package com.lebudigital.lebudigital.ui.beranda.menu.grubinformasi.fasilitasdesa

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.adapter.TvccAdapter
import com.lebudigital.lebudigital.adapter.fasilitasdesa.FasilitasAdapter
import com.lebudigital.lebudigital.adapter.profildesa.GambarDesaSlider
import com.lebudigital.lebudigital.databinding.ActivityFasilitasDesaBinding
import com.lebudigital.lebudigital.databinding.ActivityProfilDesaBinding
import com.lebudigital.lebudigital.model.fasilitas.FasilitasModel
import com.lebudigital.lebudigital.model.fasilitas.FasilitasResponse
import com.lebudigital.lebudigital.model.profildesa.GambarDesaModel
import com.lebudigital.lebudigital.model.tvcc.TvccModel

import com.lebudigital.lebudigital.session.SessionManager
import com.lebudigital.lebudigital.ui.beranda.menu.tvcc.TvccDetailActivity
import com.lebudigital.lebudigital.webservice.ApiClient
import com.lebudigital.lebudigital.webservice.Constant
import com.smarteist.autoimageslider.SliderView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FasilitasDesaActivity : AppCompatActivity(),AnkoLogger {
    lateinit var binding: ActivityFasilitasDesaBinding
    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()
    lateinit var sessionManager: SessionManager
    private lateinit var mAdapter: FasilitasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fasilitas_desa)
        binding.lifecycleOwner = this
        binding.btnback.setOnClickListener {
            finish()
        }
        progressDialog = ProgressDialog(this)
        sessionManager = SessionManager(this)
    }

    override fun onStart() {
        super.onStart()
        fasilitas()
    }

    fun fasilitas() {
        //gambar desa
        var gambarmodel = mutableListOf<GambarDesaModel>()

        //datastatistik
        binding.rvfasilitas.layoutManager = LinearLayoutManager(this)
        binding.rvfasilitas.setHasFixedSize(true)
        (binding.rvfasilitas.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL

        api.fasilitasdesa(Constant.API_KEY_BACKEND,sessionManager.getiduser()!!)
            .enqueue(object : Callback<FasilitasResponse> {
                override fun onResponse(
                    call: Call<FasilitasResponse>,
                    response: Response<FasilitasResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<FasilitasModel>()
                            val data = response.body()
                            if (data!!.fasilitas!!.isEmpty()) {

                            } else {

                                binding.txtketerangan.text = data.profil!!.deskripsi
                                for (hasil in data.fasilitas!!) {

                                    notesList.add(hasil)
                                    mAdapter = FasilitasAdapter(notesList)
                                    binding.rvfasilitas.adapter = mAdapter

                                    mAdapter.notifyDataSetChanged()
                                }

                                val imageList: ArrayList<String> = ArrayList()

                                for (gambardesa in data.gambardesa!!){
                                    gambarmodel.add(gambardesa)
                                    //slider
                                    imageList.add(Constant.STORAGE + gambardesa.foto!!)

                                }
                                setImageInSlider(imageList, binding.imageSlider, gambarmodel)

                                mAdapter.setDialog(object : FasilitasAdapter.Dialog {

                                    override fun onClick(
                                        position: Int,
                                        FasilitasModel: FasilitasModel
                                    ) {
                                        val gson = Gson()
                                        val noteJson = gson.toJson(FasilitasModel)
                                        startActivity<DetailFasilitasActivity>(
                                            "fasilitas" to noteJson
                                        )
                                    }


                                })
                            }

                        } else {
                            toast("gagal mendapatkan response")
                        }
                    } catch (e: Exception) {
                        info { "dinda ${e.message}" }
                    }
                }

                override fun onFailure(call: Call<FasilitasResponse>, t: Throwable) {
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