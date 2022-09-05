package com.lebudigital.lebudigital.ui.beranda.menu.berita

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
import com.lebudigital.lebudigital.adapter.beritadesa.BeritaDesaAdapter
import com.lebudigital.lebudigital.adapter.beritadesa.BeritaDesaPopular
import com.lebudigital.lebudigital.auth.LoginActivity
import com.lebudigital.lebudigital.databinding.ActivityBeritaDesaBinding
import com.lebudigital.lebudigital.model.beritadesa.BeritaDesaModel
import com.lebudigital.lebudigital.model.beritadesa.BeritaDesaResponse

import com.lebudigital.lebudigital.session.SessionManager
import com.lebudigital.lebudigital.session.SessionProfilManager
import com.lebudigital.lebudigital.ui.beranda.menu.grubberita.kegiatandesa.KegiatanDesaDetailActivity
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

class BeritaDesaActivity : AppCompatActivity(), AnkoLogger {
    lateinit var binding: ActivityBeritaDesaBinding
    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()
    private lateinit var mAdapter: BeritaDesaAdapter
    private lateinit var popularAdapter: BeritaDesaAdapter
    lateinit var sessionManager: SessionManager
    lateinit var sessionProfilManager: SessionProfilManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_berita_desa)
        binding.lifecycleOwner = this
        sessionManager = SessionManager(this)
        sessionProfilManager = SessionProfilManager(this)
        binding.rvberita.layoutManager = LinearLayoutManager(this)
        binding.rvberita.setHasFixedSize(true)
        (binding.rvberita.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL

        binding.btnback.setOnClickListener {
            finish()
        }

    }

    override fun onStart() {
        super.onStart()
        get_beritadesa()
        get_beritadesa_popular()
    }

    fun get_beritadesa() {
        binding.desaada.visibility = View.VISIBLE
        binding.desatidakada.visibility = View.GONE
        var slidermodel = mutableListOf<BeritaDesaModel>()
        binding.shimmermakanan.startShimmer()
        api.beritadesa(Constant.API_KEY_BACKEND, sessionManager.getiduser()!!)
            .enqueue(object : Callback<BeritaDesaResponse> {
                override fun onResponse(
                    call: Call<BeritaDesaResponse>,
                    response: Response<BeritaDesaResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<BeritaDesaModel>()
                            val data = response.body()
                            if (data!!.status == 0){
                                binding.desaada.visibility = View.GONE
                                binding.desatidakada.visibility = View.VISIBLE
                            }
                            else if (data.status == 1){
                                if (data!!.data!!.isEmpty()) {
                                    binding.shimmermakanan.stopShimmer()
                                    binding.shimmermakanan.visibility = View.GONE
                                    binding.txtnodata.visibility = View.VISIBLE
                                    binding.imgnotfound.visibility = View.VISIBLE
                                    binding.rvberita.visibility = View.GONE
                                    binding.imageSlider.visibility = View.GONE
                                }
                                else {
                                    binding.shimmermakanan.stopShimmer()
                                    binding.shimmermakanan.visibility = View.GONE
                                    binding.txtnodata.visibility = View.GONE
                                    binding.rvberita.visibility = View.VISIBLE
                                    binding.imgnotfound.visibility = View.GONE
                                    binding.imageSlider.visibility = View.VISIBLE

                                    for (hasil in data.data!!) {
                                        notesList.add(hasil)
                                        mAdapter = BeritaDesaAdapter(notesList)
                                        binding.rvberita.adapter = mAdapter

                                        mAdapter.setDialog(object : BeritaDesaAdapter.Dialog {
                                            override fun onClick(
                                                position: Int,
                                                BeritaDesaModel: BeritaDesaModel
                                            ) {
                                                val gson = Gson()
                                                val noteJson = gson.toJson(BeritaDesaModel)
                                                startActivity<BeritaDesaDetailActivity>(
                                                    "beritadesa" to noteJson
                                                )

                                            }


                                        })


                                        mAdapter.notifyDataSetChanged()
                                    }
                                    binding.srcBerita.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                                        override fun onQueryTextSubmit(p0: String?): Boolean {
                                            notesList.clear()
                                            return false
                                        }

                                        override fun onQueryTextChange(p0: String?): Boolean {
                                            search_berita(p0,notesList)
                                            return false
                                        }

                                    })

                                }

                            }
                            else if (data.status == 2){
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

                override fun onFailure(call: Call<BeritaDesaResponse>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })

    }
    fun get_beritadesa_popular() {

        var slidermodel = mutableListOf<BeritaDesaModel>()
        binding.shimmermakanan.startShimmer()
        api.beritadesa_popular(Constant.API_KEY_BACKEND, sessionManager.getiduser()!!)
            .enqueue(object : Callback<BeritaDesaResponse> {
                override fun onResponse(
                    call: Call<BeritaDesaResponse>,
                    response: Response<BeritaDesaResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<BeritaDesaModel>()
                            val data = response.body()
                            if (data!!.data!!.isEmpty()) {
                            } else {

                                val imageList: ArrayList<String> = ArrayList()

                                for (popular in data.data!!){
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

                override fun onFailure(call: Call<BeritaDesaResponse>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })

    }

    private fun setImageInSlider(
        images: ArrayList<String>,
        imageSlider: SliderView,
        sliderModel: MutableList<BeritaDesaModel>
    ) {
        val adapter = BeritaDesaPopular()
        adapter.setDialog(object : BeritaDesaPopular.Dialog {
            override fun onClick(position: Int, note: BeritaDesaModel) {
                val gson = Gson()
                val noteJson = gson.toJson(note)
                startActivity<BeritaDesaDetailActivity>(
                    "beritadesa" to noteJson
                )

            }

        })
        adapter.renewItems(images, sliderModel)
        binding.imageSlider.setSliderAdapter(adapter)
        binding.imageSlider.isAutoCycle = true
        binding.imageSlider.scrollTimeInSec = 3
        binding.imageSlider.startAutoCycle()
    }

    private fun search_berita(searchTerm: String?, notelist: MutableList<BeritaDesaModel>) {
        notelist.clear()
        if (!TextUtils.isEmpty(searchTerm)) {
            val serchtext: String =
                searchTerm!!.substring(0, 1).toUpperCase() + searchTerm.substring(1)

            api.search_berita(
                Constant.API_KEY_BACKEND,
                sessionManager.getiduser().toString(),
                serchtext
            ).enqueue(object : Callback<BeritaDesaResponse> {
                override fun onResponse(
                    call: Call<BeritaDesaResponse>,
                    response: Response<BeritaDesaResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<BeritaDesaModel>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter = BeritaDesaAdapter(notesList)
                                binding.rvberita.adapter = mAdapter

                                mAdapter.setDialog(object : BeritaDesaAdapter.Dialog {
                                    override fun onClick(
                                        position: Int,
                                        BeritaDesaModel: BeritaDesaModel
                                    ) {
                                        val gson = Gson()
                                        val noteJson = gson.toJson(BeritaDesaModel)
                                        startActivity<KegiatanDesaDetailActivity>(
                                            "kegiatandesa" to noteJson
                                        )

                                    }

                                })
                                mAdapter.notifyDataSetChanged()
                            }

                            binding.srcBerita.setOnQueryTextListener(object :
                                SearchView.OnQueryTextListener {
                                override fun onQueryTextSubmit(p0: String?): Boolean {
                                    notesList.clear()
                                    return false
                                }

                                override fun onQueryTextChange(p0: String?): Boolean {
                                    search_berita(p0, notesList)
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

                override fun onFailure(call: Call<BeritaDesaResponse>, t: Throwable) {
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
            api.beritadesa(Constant.API_KEY_BACKEND, sessionManager.getiduser()!!)
                .enqueue(object : Callback<BeritaDesaResponse> {
                    override fun onResponse(
                        call: Call<BeritaDesaResponse>,
                        response: Response<BeritaDesaResponse>
                    ) {
                        try {
                            if (response.isSuccessful) {
                                val notesList = mutableListOf<BeritaDesaModel>()
                                val data = response.body()
                                for (hasil in data!!.data!!) {
                                    notesList.add(hasil)
                                    mAdapter = BeritaDesaAdapter(notesList)
                                    binding.rvberita.adapter = mAdapter

                                    mAdapter.setDialog(object : BeritaDesaAdapter.Dialog {
                                        override fun onClick(
                                            position: Int,
                                            BeritaDesaModel: BeritaDesaModel
                                        ) {
                                            val gson = Gson()
                                            val noteJson = gson.toJson(BeritaDesaModel)
                                            startActivity<KegiatanDesaDetailActivity>(
                                                "kegiatandesa" to noteJson
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

                    override fun onFailure(call: Call<BeritaDesaResponse>, t: Throwable) {
                        info { "dinda ${t.message}" }
                    }

                })
        }
    }



}