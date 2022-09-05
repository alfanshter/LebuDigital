package com.lebudigital.lebudigital.ui.beranda.menu.grubinformasi.fasilitasdesa

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
import com.lebudigital.lebudigital.adapter.fasilitasdesa.FasilitasAdapter
import com.lebudigital.lebudigital.adapter.profildesa.GambarDesaSlider
import com.lebudigital.lebudigital.auth.LoginActivity
import com.lebudigital.lebudigital.databinding.ActivityFasilitasDesaBinding
import com.lebudigital.lebudigital.model.fasilitas.FasilitasModel
import com.lebudigital.lebudigital.model.fasilitas.FasilitasResponse
import com.lebudigital.lebudigital.model.profildesa.GambarDesaModel

import com.lebudigital.lebudigital.session.SessionManager
import com.lebudigital.lebudigital.session.SessionProfilManager
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
    lateinit var sessionProfilManager: SessionProfilManager
    private lateinit var mAdapter: FasilitasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fasilitas_desa)
        binding.lifecycleOwner = this
        sessionProfilManager = SessionProfilManager(this)

        binding.btnback.setOnClickListener {
            finish()
        }
        progressDialog = ProgressDialog(this)

        sessionManager = SessionManager(this)
        binding.shimmermakanan.startShimmer()
    }

    override fun onStart() {
        super.onStart()
        fasilitas()
    }

    fun fasilitas() {
        binding.desaada.visibility = View.VISIBLE
        binding.desatidakada.visibility = View.GONE

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

                            if (response.body()!!.status == 0){
                                binding.desaada.visibility = View.GONE
                                binding.desatidakada.visibility = View.VISIBLE
                            }
                            else if (response.body()!!.status == 1){
                                binding.txtketerangan.text = data!!.profil!!.deskripsi
                                binding.txtnamadesa.text = data.profil!!.desa!!.name

                                if (data.gambardesa!!.isEmpty()){
                                    binding.imgnotfound.visibility = View.VISIBLE
                                    binding.imageSlider.visibility = View.GONE
                                }
                                else{
                                    binding.imgnotfound.visibility = View.GONE
                                    binding.imageSlider.visibility = View.VISIBLE
                                    val imageList: ArrayList<String> = ArrayList()

                                    for (gambardesa in data.gambardesa!!){
                                        gambarmodel.add(gambardesa)
                                        //slider
                                        imageList.add(Constant.STORAGE + gambardesa.foto!!)

                                    }
                                    setImageInSlider(imageList, binding.imageSlider, gambarmodel)

                                }
                                if (data.fasilitas!!.isEmpty()) {
                                    binding.rvfasilitas.visibility = View.GONE
                                    binding.imgkosong.visibility = View.VISIBLE
                                    binding.shimmermakanan.visibility = View.GONE
                                }
                                else {
                                    binding.rvfasilitas.visibility = View.VISIBLE
                                    binding.shimmermakanan.visibility = View.GONE
                                    binding.imgkosong.visibility = View.GONE
                                    binding.shimmermakanan.stopShimmer()

                                    for (hasil in data.fasilitas!!) {

                                        notesList.add(hasil)
                                        mAdapter = FasilitasAdapter(notesList)
                                        binding.rvfasilitas.adapter = mAdapter

                                        mAdapter.notifyDataSetChanged()
                                    }


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

                            }
                            if (response.body()!!.status == 2){
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