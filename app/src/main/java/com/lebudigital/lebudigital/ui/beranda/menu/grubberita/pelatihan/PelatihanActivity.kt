package com.lebudigital.lebudigital.ui.beranda.menu.grubberita.pelatihan

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.lebudigital.lebudigital.MainActivity
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.adapter.pelatihan.PelatihanDesaAdapter
import com.lebudigital.lebudigital.adapter.pelatihan.PelatihanSlider
import com.lebudigital.lebudigital.auth.LoginActivity
import com.lebudigital.lebudigital.databinding.ActivityPelatihanBinding
import com.lebudigital.lebudigital.model.pelatihan.PelatihanMinggu
import com.lebudigital.lebudigital.model.pelatihan.PelatihanResponse
import com.lebudigital.lebudigital.session.SessionManager
import com.lebudigital.lebudigital.session.SessionProfilManager
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
    lateinit var sessionProfilManager: SessionProfilManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pelatihan)
        binding.lifecycleOwner = this
        sessionManager = SessionManager(this)
        sessionProfilManager = SessionProfilManager(this)


        binding.btnback.setOnClickListener {
            finish()
        }

    }

    override fun onStart() {
        super.onStart()
        get_pelatihan()

    }

    fun get_pelatihan() {
        binding.desaada.visibility = View.VISIBLE
        binding.desatidakada.visibility = View.GONE

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


                            if (data!!.status == 0){
                                binding.desaada.visibility = View.GONE
                                binding.desatidakada.visibility = View.VISIBLE
                            }

                            if (data.status == 1){
                                val imageList: ArrayList<String> = ArrayList()

                                for (popular in data.pelatihanAkanDatang!!){
                                    slidermodel.add(popular)
                                    //slider
                                    imageList.add(Constant.STORAGE + popular.foto!!)

                                }
                                setImageInSlider(imageList, binding.imageSlider, slidermodel)


                                if (data.pelatihanAkanDatang!!.isEmpty()){
                                    binding.gambarkosong.visibility = View.VISIBLE
                                    binding.imageSlider.visibility = View.GONE
                                }else{
                                    binding.gambarkosong.visibility = View.GONE
                                    binding.imageSlider.visibility = View.VISIBLE
                                }
                                if (data.pelatihanMingguIni!!.isEmpty()) {
                                    binding.shimmermakanan.stopShimmer()
                                    binding.shimmermakanan.visibility = View.GONE
                                    binding.txtnodata.visibility = View.VISIBLE
                                    binding.rvpelatihan.visibility = View.GONE
                                }
                                else {
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

                                    binding.srcPelatihan.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                                        override fun onQueryTextSubmit(p0: String?): Boolean {
                                            notesList.clear()
                                            return false
                                        }

                                        override fun onQueryTextChange(p0: String?): Boolean {
                                            search_pelatihan(p0,notesList)
                                            return false
                                        }

                                    })



                                }

                            }
                            if (data.status == 2){
                                sessionManager.setLogin(false)
                                sessionManager.setLoginadmin(false)
                                sessionProfilManager.setAlamat("")
                                sessionProfilManager.setNik("")
                                sessionProfilManager.setTelepon("")
                                sessionProfilManager.setNama("")
                                sessionProfilManager.setEmail("")
                                toast("Akun anda telah dihapus")
                                startActivity<LoginActivity>()
                                finish()
                                MainActivity.activity.finish()
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

    private fun search_pelatihan(searchTerm: String?, notelist: MutableList<PelatihanMinggu>) {
        notelist.clear()
        if (!TextUtils.isEmpty(searchTerm)) {
            val serchtext: String =
                searchTerm!!.substring(0, 1).toUpperCase() + searchTerm.substring(1)

            api.search_pelatihan(
                Constant.API_KEY_BACKEND,
                sessionManager.getiduser().toString(),
                serchtext
            ).enqueue(object : Callback<PelatihanResponse> {
                override fun onResponse(
                    call: Call<PelatihanResponse>,
                    response: Response<PelatihanResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<PelatihanMinggu>()
                            val data = response.body()
                            for (hasil in data!!.pelatihanMingguIni!!) {
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

                            binding.srcPelatihan.setOnQueryTextListener(object :
                                SearchView.OnQueryTextListener {
                                override fun onQueryTextSubmit(p0: String?): Boolean {
                                    notesList.clear()
                                    return false
                                }

                                override fun onQueryTextChange(p0: String?): Boolean {
                                    search_pelatihan(p0, notesList)
                                    return false
                                }

                            })
                        } else {
                            toast("gagal mendapatkan response")
                        }
                    } catch (e: Exception) {
                        info { "dinda ${e.message}" }
                    }
                }

                override fun onFailure(call: Call<PelatihanResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })


            val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(
                this,
                RecyclerView.VERTICAL,
                false
            )

        } else {
            notelist.clear()
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
                                for (hasil in data!!.pelatihanMingguIni!!) {
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