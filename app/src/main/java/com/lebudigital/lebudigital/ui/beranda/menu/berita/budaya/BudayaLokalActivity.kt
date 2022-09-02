package com.lebudigital.lebudigital.ui.beranda.menu.berita.budaya

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.lebudigital.lebudigital.MainActivity
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.adapter.budayalokal.BudayaLokalAdapter
import com.lebudigital.lebudigital.auth.LoginActivity
import com.lebudigital.lebudigital.databinding.ActivityBudayaLokalBinding
import com.lebudigital.lebudigital.model.beritadesa.BeritaDesaResponse
import com.lebudigital.lebudigital.model.budayalokal.BudayaLokalModel
import com.lebudigital.lebudigital.model.budayalokal.BudayaLokalResponse
import com.lebudigital.lebudigital.session.SessionManager
import com.lebudigital.lebudigital.session.SessionProfilManager
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


class BudayaLokalActivity : AppCompatActivity(), AnkoLogger {
    lateinit var binding: ActivityBudayaLokalBinding
    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()
    private lateinit var mAdapter: BudayaLokalAdapter
    lateinit var sessionManager: SessionManager
    lateinit var sessionProfilManager: SessionProfilManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_budaya_lokal)
        binding.lifecycleOwner = this
        sessionManager = SessionManager(this)
        sessionProfilManager = SessionProfilManager(this)
        binding.rvbudayaloka.layoutManager = GridLayoutManager(this,2)
        binding.rvbudayaloka.setHasFixedSize(true)
        (binding.rvbudayaloka.layoutManager as GridLayoutManager).orientation =
            LinearLayoutManager.VERTICAL

        binding.btnback.setOnClickListener {
            finish()
        }

    }

    override fun onStart() {
        super.onStart()
        budayalokal()

    }

    fun budayalokal() {
        var slidermodel = mutableListOf<BudayaLokalModel>()
        binding.shimmermakanan.startShimmer()
        api.budayalokal(Constant.API_KEY_BACKEND, sessionManager.getiduser()!!)
            .enqueue(object : Callback<BudayaLokalResponse> {
                override fun onResponse(
                    call: Call<BudayaLokalResponse>,
                    response: Response<BudayaLokalResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<BudayaLokalModel>()
                            val data = response.body()
                            if (data!!.status == 0){
                                binding.desaada.visibility = View.GONE
                                binding.desatidakada.visibility = View.VISIBLE
                            }

                            else if (data.status == 1){
                                if (data.data!!.isEmpty()) {
                                    binding.shimmermakanan.stopShimmer()
                                    binding.shimmermakanan.visibility = View.GONE
                                    binding.txtnodata.visibility = View.VISIBLE
                                    binding.rvbudayaloka.visibility = View.GONE
                                }
                                else {
                                    binding.shimmermakanan.stopShimmer()
                                    binding.shimmermakanan.visibility = View.GONE
                                    binding.txtnodata.visibility = View.GONE
                                    binding.rvbudayaloka.visibility = View.VISIBLE


                                    for (hasil in data.data!!) {
                                        notesList.add(hasil)
                                        mAdapter = BudayaLokalAdapter(notesList)
                                        binding.rvbudayaloka.adapter = mAdapter

                                        mAdapter.setDialog(object : BudayaLokalAdapter.Dialog {


                                            override fun onClick(
                                                position: Int,
                                                BudayaLokalModel: BudayaLokalModel
                                            ) {
                                                val gson = Gson()
                                                val noteJson = gson.toJson(BudayaLokalModel)
                                                startActivity<BeritaDesaDetailActivity>(
                                                    "budayalokal" to noteJson
                                                )

                                            }


                                        })


                                        mAdapter.notifyDataSetChanged()
                                    }
                                    binding.srcbudayalokal.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                                        override fun onQueryTextSubmit(p0: String?): Boolean {
                                            notesList.clear()
                                            return false
                                        }

                                        override fun onQueryTextChange(p0: String?): Boolean {
                                            search_budayalokal(p0,notesList)
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

                override fun onFailure(call: Call<BudayaLokalResponse>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })

    }

    private fun search_budayalokal(searchTerm: String?,notelist : MutableList<BudayaLokalModel>) {
        notelist.clear()
        if (!TextUtils.isEmpty(searchTerm)) {
            val serchtext: String =
                searchTerm!!.substring(0, 1).toUpperCase() + searchTerm.substring(1)

            api.search_budayalokal(Constant.API_KEY_BACKEND,sessionManager.getiduser().toString(),serchtext).enqueue(object : Callback<BudayaLokalResponse>{
                override fun onResponse(
                    call: Call<BudayaLokalResponse>,
                    response: Response<BudayaLokalResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<BudayaLokalModel>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter = BudayaLokalAdapter(notesList)
                                binding.rvbudayaloka.adapter = mAdapter

                                mAdapter.setDialog(object : BudayaLokalAdapter.Dialog{
                                    override fun onClick(position: Int, BudayaLokalModel: BudayaLokalModel) {
                                        val gson = Gson()
                                        val noteJson = gson.toJson(BudayaLokalModel)
                                        startActivity<BeritaDesaDetailActivity>(
                                            "budayalokal" to noteJson
                                        )

                                    }

                                })
                                mAdapter.notifyDataSetChanged()
                            }

                            binding.srcbudayalokal.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                                override fun onQueryTextSubmit(p0: String?): Boolean {
                                    notesList.clear()
                                    return false
                                }

                                override fun onQueryTextChange(p0: String?): Boolean {
                                    search_budayalokal(p0,notesList)
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

                override fun onFailure(call: Call<BudayaLokalResponse>, t: Throwable) {
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
            api.budayalokal(Constant.API_KEY_BACKEND,sessionManager.getiduser()!!)
                .enqueue(object : Callback<BudayaLokalResponse> {
                    override fun onResponse(
                        call: Call<BudayaLokalResponse>,
                        response: Response<BudayaLokalResponse>
                    ) {
                        try {
                            if (response.isSuccessful) {
                                val notesList = mutableListOf<BudayaLokalModel>()
                                val data = response.body()
                                for (hasil in data!!.data!!) {
                                    notesList.add(hasil)
                                    mAdapter = BudayaLokalAdapter(notesList)
                                    binding.rvbudayaloka.adapter = mAdapter

                                    mAdapter.setDialog(object : BudayaLokalAdapter.Dialog{
                                        override fun onClick(position: Int, BudayaLokalModel: BudayaLokalModel) {
                                            val gson = Gson()
                                            val noteJson = gson.toJson(BudayaLokalModel)
                                            startActivity<BeritaDesaDetailActivity>(
                                                "budayalokal" to noteJson
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

                    override fun onFailure(call: Call<BudayaLokalResponse>, t: Throwable) {
                        info { "dinda ${t.message}" }
                    }

                })
        }
    }




}