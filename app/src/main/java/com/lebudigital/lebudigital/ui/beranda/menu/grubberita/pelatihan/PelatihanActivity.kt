package com.lebudigital.lebudigital.ui.beranda.menu.grubberita.pelatihan

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.adapter.pelatihan.PelatihanDesaAdapter
import com.lebudigital.lebudigital.adapter.pelatihan.PelatihanSlider
import com.lebudigital.lebudigital.databinding.ActivityBeritaDesaBinding
import com.lebudigital.lebudigital.databinding.ActivityPelatihanBinding
import com.lebudigital.lebudigital.model.pelatihan.PelatihanMinggu
import com.lebudigital.lebudigital.model.pelatihan.PelatihanResponse
import com.lebudigital.lebudigital.session.SessionManager
import com.lebudigital.lebudigital.ui.beranda.menu.berita.BeritaDesaDetailActivity
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

class PelatihanActivity : AppCompatActivity(), AnkoLogger {
    lateinit var binding: ActivityPelatihanBinding
    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()
    private lateinit var mAdapter: PelatihanDesaAdapter
    private lateinit var popularAdapter: PelatihanDesaAdapter
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pelatihan)
        binding.lifecycleOwner = this
        sessionManager = SessionManager(this)


        binding.btnback.setOnClickListener {
            finish()
        }

    }

    override fun onStart() {
        super.onStart()
        get_beritadesa()

    }

    fun get_beritadesa() {
        binding.rvpelatihan.layoutManager = LinearLayoutManager(this)
        binding.rvpelatihan.setHasFixedSize(true)
        (binding.rvpelatihan.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL

        var slidermodel = mutableListOf<PelatihanMinggu>()
        binding.shimmermakanan.startShimmer()
        api.pelatihan(Constant.API_KEY_BACKEND, sessionManager.getiduser()!!)
            .enqueue(object : Callback<PelatihanResponse> {
                override fun onResponse(
                    call: Call<PelatihanResponse>,
                    response: Response<PelatihanResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<PelatihanMinggu>()
                            val data = response.body()
                            if (data!!.pelatihanMingguIni!!.isEmpty()) {
                                binding.shimmermakanan.stopShimmer()
                                binding.shimmermakanan.visibility = View.GONE
                                binding.txtnodata.visibility = View.VISIBLE
                                binding.rvpelatihan.visibility = View.GONE
                            } else {
                                binding.shimmermakanan.stopShimmer()
                                binding.shimmermakanan.visibility = View.GONE
                                binding.txtnodata.visibility = View.GONE
                                binding.rvpelatihan.visibility = View.VISIBLE

                                for (hasil in data.pelatihanMingguIni!!) {
                                    notesList.add(hasil)
                                    mAdapter = PelatihanDesaAdapter(notesList)
                                    binding.rvpelatihan.adapter = mAdapter

                                    mAdapter.setDialog(object : PelatihanDesaAdapter.Dialog {
                                        override fun onClick(
                                            position: Int,
                                            PelatihanMinggu: PelatihanMinggu
                                        ) {
                                            val gson = Gson()
                                            val noteJson = gson.toJson(PelatihanMinggu)
                                            startActivity<DetailPelatihanActivity>(
                                                "pelatihan" to noteJson
                                            )

                                        }


                                    })
                                    mAdapter.notifyDataSetChanged()
                                }

                                val imageList: ArrayList<String> = ArrayList()

                                for (popular in data.pelatihanAkanDatang!!){
                                    slidermodel.add(popular)
                                    //slider
                                    imageList.add(Constant.STORAGE + popular.foto!!)

                                }
                                setImageInSlider(imageList, binding.imageSlider, slidermodel)



                            }

                        } else {
                            toast("gagal mendapatkan response")
                        }
                    } catch (e: Exception) {
                        info { "dinda ${e.message}" }
                    }
                }

                override fun onFailure(call: Call<PelatihanResponse>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })

    }
    private fun setImageInSlider(
        images: ArrayList<String>,
        imageSlider: SliderView,
        sliderModel: MutableList<PelatihanMinggu>
    ) {
        val adapter = PelatihanSlider()
        adapter.setDialog(object : PelatihanSlider.Dialog {

            override fun onClick(position: Int, note: PelatihanMinggu) {
                val gson = Gson()
                val noteJson = gson.toJson(note)
                startActivity<DetailPelatihanActivity>(
                    "pelatihan" to noteJson
                )

            }

        })
        adapter.renewItems(images, sliderModel)
        binding.imageSlider.setSliderAdapter(adapter)
        binding.imageSlider.isAutoCycle = true
        binding.imageSlider.scrollTimeInSec = 3
        binding.imageSlider.startAutoCycle()
    }


}