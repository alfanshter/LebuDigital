package com.lebudigital.lebudigital.ui.beranda.menu.grubberita.kegiatandesa

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.lebudigital.lebudigital.MainActivity
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.adapter.kegiatandesa.KegiatanDesaAdapter
import com.lebudigital.lebudigital.auth.LoginActivity
import com.lebudigital.lebudigital.databinding.ActivityKegiatanDesaBinding
import com.lebudigital.lebudigital.model.kegiatandesa.KegiatanDesaModel
import com.lebudigital.lebudigital.model.profildesa.KegiatanDesaResponse
import com.lebudigital.lebudigital.session.SessionManager
import com.lebudigital.lebudigital.session.SessionProfilManager
import com.lebudigital.lebudigital.webservice.ApiClient
import com.lebudigital.lebudigital.webservice.Constant
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KegiatanDesaActivity : AppCompatActivity(), AnkoLogger {
    lateinit var binding: ActivityKegiatanDesaBinding
    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()
    private lateinit var mAdapter: KegiatanDesaAdapter
    var menu: String? = null
    lateinit var sessionManager: SessionManager
    lateinit var sessionProfilManager: SessionProfilManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_kegiatan_desa)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)
        sessionManager = SessionManager(this)
        sessionProfilManager = SessionProfilManager(this)

        binding.rvkegiatandesa.layoutManager = LinearLayoutManager(this, )
        binding.rvkegiatandesa.setHasFixedSize(true)
        (binding.rvkegiatandesa.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL

        binding.btnback.setOnClickListener {
            finish()
        }



    }

    override fun onStart() {
        super.onStart()
        get_kegiatandesa()

    }


    fun get_kegiatandesa() {
        binding.desaada.visibility = View.VISIBLE
        binding.desatidakada.visibility = View.GONE
        binding.shimmermakanan.startShimmer()
        api.kegiatandesa(Constant.API_KEY_BACKEND,sessionManager.getiduser()!!)
            .enqueue(object : Callback<KegiatanDesaResponse> {
                override fun onResponse(
                    call: Call<KegiatanDesaResponse>,
                    response: Response<KegiatanDesaResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<KegiatanDesaModel>()
                            val data = response.body()

                            if (data!!.status == 0){
                                binding.desaada.visibility = View.GONE
                                binding.desatidakada.visibility = View.VISIBLE
                            }

                            if (data.status == 1){
                                if (data.kegiatandesa!!.isEmpty()) {
                                    binding.shimmermakanan.stopShimmer()
                                    binding.shimmermakanan.visibility = View.GONE
                                    binding.txtnodata.visibility = View.VISIBLE
                                    binding.rvkegiatandesa.visibility = View.GONE
                                }
                                else {
                                    binding.shimmermakanan.stopShimmer()
                                    binding.shimmermakanan.visibility = View.GONE
                                    binding.txtnodata.visibility = View.GONE
                                    binding.rvkegiatandesa.visibility = View.VISIBLE

                                    for (hasil in data.kegiatandesa!!) {
                                        notesList.add(hasil)
                                        mAdapter = KegiatanDesaAdapter(notesList)
                                        binding.rvkegiatandesa.adapter = mAdapter

                                        mAdapter.setDialog(object : KegiatanDesaAdapter.Dialog {


                                            override fun onClick(
                                                position: Int,
                                                KegiatanDesaModel: KegiatanDesaModel
                                            ) {
                                                val gson = Gson()
                                                val noteJson = gson.toJson(KegiatanDesaModel)
                                                startActivity<KegiatanDesaDetailActivity>(
                                                    "kegiatandesa" to noteJson
                                                )
                                            }


                                        })
                                        mAdapter.notifyDataSetChanged()
                                    }

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

                override fun onFailure(call: Call<KegiatanDesaResponse>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })

    }



}