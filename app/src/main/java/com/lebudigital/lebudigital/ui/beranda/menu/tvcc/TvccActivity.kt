package com.lebudigital.lebudigital.ui.beranda.menu.tvcc

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
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.adapter.TvccAdapter
import com.lebudigital.lebudigital.databinding.ActivityTvccBinding
import com.lebudigital.lebudigital.model.tvcc.TvccModel
import com.lebudigital.lebudigital.model.tvcc.TvccResponse
import com.lebudigital.lebudigital.webservice.ApiClient
import com.lebudigital.lebudigital.webservice.Constant
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvccActivity : AppCompatActivity(), AnkoLogger {
    lateinit var binding: ActivityTvccBinding
    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()
    private lateinit var mAdapter: TvccAdapter
    var menu: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tvcc)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)

        val bundle: Bundle? = intent.extras
        menu = bundle!!.getString("menu")

        binding.txtmenu.text = menu

        binding.rvtvcc.layoutManager = LinearLayoutManager(this)
        binding.rvtvcc.setHasFixedSize(true)
        (binding.rvtvcc.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL

        binding.btnback.setOnClickListener {
            finish()
        }

        if (menu == "TVCC") {
            get_tvcc()
        } else if (menu == "LADA") {
            get_lada()
        } else if (menu == "BAYAR/BELI") {
            get_bayarbeli()
        } else if (menu == "PASAR DESA") {
            get_pasardesa()
        }


    }

    override fun onStart() {
        super.onStart()

    }


    fun get_tvcc() {
        binding.shimmermakanan.startShimmer()
        api.tvcc(Constant.API_KEY_BACKEND)
            .enqueue(object : Callback<TvccResponse> {
                override fun onResponse(
                    call: Call<TvccResponse>,
                    response: Response<TvccResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<TvccModel>()
                            val data = response.body()
                            if (data!!.data!!.isEmpty()) {
                                binding.shimmermakanan.stopShimmer()
                                binding.shimmermakanan.visibility = View.GONE
                                binding.txtnodata.visibility = View.VISIBLE
                                binding.rvtvcc.visibility = View.GONE
                            } else {
                                binding.shimmermakanan.stopShimmer()
                                binding.shimmermakanan.visibility = View.GONE
                                binding.txtnodata.visibility = View.GONE
                                binding.rvtvcc.visibility = View.VISIBLE

                                for (hasil in data.data!!) {
                                    notesList.add(hasil)
                                    mAdapter = TvccAdapter(notesList)
                                    binding.rvtvcc.adapter = mAdapter

                                    mAdapter.setDialog(object : TvccAdapter.Dialog {
                                        override fun onClick(
                                            position: Int,
                                            TvccModel: TvccModel
                                        ) {
                                            val gson = Gson()
                                            val noteJson = gson.toJson(TvccModel)
                                            startActivity<TvccDetailActivity>(
                                                "tvcc" to noteJson,
                                                "menu" to menu
                                            )

                                        }


                                    })
                                    mAdapter.notifyDataSetChanged()
                                }

                                binding.srcTvcc.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                                    override fun onQueryTextSubmit(p0: String?): Boolean {
                                        notesList.clear()
                                        return false
                                    }

                                    override fun onQueryTextChange(p0: String?): Boolean {
                                        search_tvcc(p0,notesList)
                                        return false
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

                override fun onFailure(call: Call<TvccResponse>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })

    }

    private fun search_tvcc(searchTerm: String?,notelist : MutableList<TvccModel>) {
        notelist.clear()
        if (!TextUtils.isEmpty(searchTerm)) {
            val serchtext: String =
                searchTerm!!.substring(0, 1).toUpperCase() + searchTerm.substring(1)

            api.search_tvcc(Constant.API_KEY_BACKEND,serchtext).enqueue(object : Callback<TvccResponse>{
                override fun onResponse(
                    call: Call<TvccResponse>,
                    response: Response<TvccResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<TvccModel>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter = TvccAdapter(notesList)
                                binding.rvtvcc.adapter = mAdapter

                                mAdapter.setDialog(object : TvccAdapter.Dialog{
                                    override fun onClick(position: Int, TvccModel: TvccModel) {
                                        val gson = Gson()
                                        val noteJson = gson.toJson(TvccModel)
                                        startActivity<TvccDetailActivity>(
                                            "tvcc" to noteJson,
                                            "menu" to menu
                                        )

                                    }

                                })
                                mAdapter.notifyDataSetChanged()
                            }

                            binding.srcTvcc.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                                override fun onQueryTextSubmit(p0: String?): Boolean {
                                    notesList.clear()
                                    return false
                                }

                                override fun onQueryTextChange(p0: String?): Boolean {
                                    search_tvcc(p0,notesList)
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

                override fun onFailure(call: Call<TvccResponse>, t: Throwable) {
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
            api.tvcc(Constant.API_KEY_BACKEND)
                .enqueue(object : Callback<TvccResponse> {
                    override fun onResponse(
                        call: Call<TvccResponse>,
                        response: Response<TvccResponse>
                    ) {
                        try {
                            if (response.isSuccessful) {
                                val notesList = mutableListOf<TvccModel>()
                                val data = response.body()
                                for (hasil in data!!.data!!) {
                                    notesList.add(hasil)
                                    mAdapter = TvccAdapter(notesList)
                                    binding.rvtvcc.adapter = mAdapter

                                    mAdapter.setDialog(object : TvccAdapter.Dialog{
                                        override fun onClick(position: Int, TvccModel: TvccModel) {
                                            val gson = Gson()
                                            val noteJson = gson.toJson(TvccModel)
                                            startActivity<TvccDetailActivity>(
                                                "tvcc" to noteJson,
                                                "menu" to menu
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

                    override fun onFailure(call: Call<TvccResponse>, t: Throwable) {
                        info { "dinda ${t.message}" }
                    }

                })
        }
    }
    private fun search_bayarbeli(searchTerm: String?,notelist : MutableList<TvccModel>) {
        notelist.clear()
        if (!TextUtils.isEmpty(searchTerm)) {
            val serchtext: String =
                searchTerm!!.substring(0, 1).toUpperCase() + searchTerm.substring(1)

            api.search_bayarbeli(Constant.API_KEY_BACKEND,serchtext).enqueue(object : Callback<TvccResponse>{
                override fun onResponse(
                    call: Call<TvccResponse>,
                    response: Response<TvccResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<TvccModel>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter = TvccAdapter(notesList)
                                binding.rvtvcc.adapter = mAdapter

                                mAdapter.setDialog(object : TvccAdapter.Dialog{
                                    override fun onClick(position: Int, TvccModel: TvccModel) {
                                        val gson = Gson()
                                        val noteJson = gson.toJson(TvccModel)
                                        startActivity<TvccDetailActivity>(
                                            "tvcc" to noteJson,
                                            "menu" to menu
                                        )

                                    }

                                })
                                mAdapter.notifyDataSetChanged()
                            }

                            binding.srcTvcc.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                                override fun onQueryTextSubmit(p0: String?): Boolean {
                                    notesList.clear()
                                    return false
                                }

                                override fun onQueryTextChange(p0: String?): Boolean {
                                    search_bayarbeli(p0,notesList)
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

                override fun onFailure(call: Call<TvccResponse>, t: Throwable) {
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
            api.bayarbeli(Constant.API_KEY_BACKEND)
                .enqueue(object : Callback<TvccResponse> {
                    override fun onResponse(
                        call: Call<TvccResponse>,
                        response: Response<TvccResponse>
                    ) {
                        try {
                            if (response.isSuccessful) {
                                val notesList = mutableListOf<TvccModel>()
                                val data = response.body()
                                for (hasil in data!!.data!!) {
                                    notesList.add(hasil)
                                    mAdapter = TvccAdapter(notesList)
                                    binding.rvtvcc.adapter = mAdapter

                                    mAdapter.setDialog(object : TvccAdapter.Dialog{
                                        override fun onClick(position: Int, TvccModel: TvccModel) {
                                            val gson = Gson()
                                            val noteJson = gson.toJson(TvccModel)
                                            startActivity<TvccDetailActivity>(
                                                "tvcc" to noteJson,
                                                "menu" to menu
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

                    override fun onFailure(call: Call<TvccResponse>, t: Throwable) {
                        info { "dinda ${t.message}" }
                    }

                })
        }
    }
    private fun search_pasardesa(searchTerm: String?,notelist : MutableList<TvccModel>) {
        notelist.clear()
        if (!TextUtils.isEmpty(searchTerm)) {
            val serchtext: String =
                searchTerm!!.substring(0, 1).toUpperCase() + searchTerm.substring(1)

            api.search_pasardesa(Constant.API_KEY_BACKEND,serchtext).enqueue(object : Callback<TvccResponse>{
                override fun onResponse(
                    call: Call<TvccResponse>,
                    response: Response<TvccResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<TvccModel>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter = TvccAdapter(notesList)
                                binding.rvtvcc.adapter = mAdapter

                                mAdapter.setDialog(object : TvccAdapter.Dialog{
                                    override fun onClick(position: Int, TvccModel: TvccModel) {
                                        val gson = Gson()
                                        val noteJson = gson.toJson(TvccModel)
                                        startActivity<TvccDetailActivity>(
                                            "tvcc" to noteJson,
                                            "menu" to menu
                                        )

                                    }

                                })
                                mAdapter.notifyDataSetChanged()
                            }

                            binding.srcTvcc.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                                override fun onQueryTextSubmit(p0: String?): Boolean {
                                    notesList.clear()
                                    return false
                                }

                                override fun onQueryTextChange(p0: String?): Boolean {
                                    search_pasardesa(p0,notesList)
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

                override fun onFailure(call: Call<TvccResponse>, t: Throwable) {
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
            api.pasardesa(Constant.API_KEY_BACKEND)
                .enqueue(object : Callback<TvccResponse> {
                    override fun onResponse(
                        call: Call<TvccResponse>,
                        response: Response<TvccResponse>
                    ) {
                        try {
                            if (response.isSuccessful) {
                                val notesList = mutableListOf<TvccModel>()
                                val data = response.body()
                                for (hasil in data!!.data!!) {
                                    notesList.add(hasil)
                                    mAdapter = TvccAdapter(notesList)
                                    binding.rvtvcc.adapter = mAdapter

                                    mAdapter.setDialog(object : TvccAdapter.Dialog{
                                        override fun onClick(position: Int, TvccModel: TvccModel) {
                                            val gson = Gson()
                                            val noteJson = gson.toJson(TvccModel)
                                            startActivity<TvccDetailActivity>(
                                                "tvcc" to noteJson,
                                                "menu" to menu
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

                    override fun onFailure(call: Call<TvccResponse>, t: Throwable) {
                        info { "dinda ${t.message}" }
                    }

                })
        }
    }
    private fun search_lada(searchTerm: String?,notelist : MutableList<TvccModel>) {
        notelist.clear()
        if (!TextUtils.isEmpty(searchTerm)) {
            val serchtext: String =
                searchTerm!!.substring(0, 1).toUpperCase() + searchTerm.substring(1)

            api.search_lada(Constant.API_KEY_BACKEND,serchtext).enqueue(object : Callback<TvccResponse>{
                override fun onResponse(
                    call: Call<TvccResponse>,
                    response: Response<TvccResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<TvccModel>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter = TvccAdapter(notesList)
                                binding.rvtvcc.adapter = mAdapter

                                mAdapter.setDialog(object : TvccAdapter.Dialog{
                                    override fun onClick(position: Int, TvccModel: TvccModel) {
                                        val gson = Gson()
                                        val noteJson = gson.toJson(TvccModel)
                                        startActivity<TvccDetailActivity>(
                                            "tvcc" to noteJson,
                                            "menu" to menu
                                        )

                                    }

                                })
                                mAdapter.notifyDataSetChanged()
                            }

                            binding.srcTvcc.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                                override fun onQueryTextSubmit(p0: String?): Boolean {
                                    notesList.clear()
                                    return false
                                }

                                override fun onQueryTextChange(p0: String?): Boolean {
                                    search_pasardesa(p0,notesList)
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

                override fun onFailure(call: Call<TvccResponse>, t: Throwable) {
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
            api.lada(Constant.API_KEY_BACKEND)
                .enqueue(object : Callback<TvccResponse> {
                    override fun onResponse(
                        call: Call<TvccResponse>,
                        response: Response<TvccResponse>
                    ) {
                        try {
                            if (response.isSuccessful) {
                                val notesList = mutableListOf<TvccModel>()
                                val data = response.body()
                                for (hasil in data!!.data!!) {
                                    notesList.add(hasil)
                                    mAdapter = TvccAdapter(notesList)
                                    binding.rvtvcc.adapter = mAdapter

                                    mAdapter.setDialog(object : TvccAdapter.Dialog{
                                        override fun onClick(position: Int, TvccModel: TvccModel) {
                                            val gson = Gson()
                                            val noteJson = gson.toJson(TvccModel)
                                            startActivity<TvccDetailActivity>(
                                                "tvcc" to noteJson,
                                                "menu" to menu
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

                    override fun onFailure(call: Call<TvccResponse>, t: Throwable) {
                        info { "dinda ${t.message}" }
                    }

                })
        }
    }


    fun get_lada() {
        binding.shimmermakanan.startShimmer()
        api.lada(Constant.API_KEY_BACKEND)
            .enqueue(object : Callback<TvccResponse> {
                override fun onResponse(
                    call: Call<TvccResponse>,
                    response: Response<TvccResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<TvccModel>()
                            val data = response.body()
                            if (data!!.data!!.isEmpty()) {
                                binding.shimmermakanan.stopShimmer()
                                binding.shimmermakanan.visibility = View.GONE
                                binding.txtnodata.visibility = View.VISIBLE
                                binding.rvtvcc.visibility = View.GONE
                            } else {
                                binding.shimmermakanan.stopShimmer()
                                binding.shimmermakanan.visibility = View.GONE
                                binding.txtnodata.visibility = View.GONE
                                binding.rvtvcc.visibility = View.VISIBLE

                                for (hasil in data.data!!) {
                                    notesList.add(hasil)
                                    mAdapter = TvccAdapter(notesList)
                                    binding.rvtvcc.adapter = mAdapter

                                    mAdapter.setDialog(object : TvccAdapter.Dialog {
                                        override fun onClick(
                                            position: Int,
                                            TvccModel: TvccModel
                                        ) {
                                            val gson = Gson()
                                            val noteJson = gson.toJson(TvccModel)
                                            startActivity<TvccDetailActivity>(
                                                "tvcc" to noteJson,
                                                "menu" to menu
                                            )

                                        }


                                    })
                                    mAdapter.notifyDataSetChanged()
                                }

                                binding.srcTvcc.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                                    override fun onQueryTextSubmit(p0: String?): Boolean {
                                        notesList.clear()
                                        return false
                                    }

                                    override fun onQueryTextChange(p0: String?): Boolean {
                                        search_lada(p0,notesList)
                                        return false
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

                override fun onFailure(call: Call<TvccResponse>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })

    }

    fun get_bayarbeli() {
        binding.shimmermakanan.startShimmer()
        api.bayarbeli(Constant.API_KEY_BACKEND)
            .enqueue(object : Callback<TvccResponse> {
                override fun onResponse(
                    call: Call<TvccResponse>,
                    response: Response<TvccResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<TvccModel>()
                            val data = response.body()
                            if (data!!.data!!.isEmpty()) {
                                binding.shimmermakanan.stopShimmer()
                                binding.shimmermakanan.visibility = View.GONE
                                binding.txtnodata.visibility = View.VISIBLE
                                binding.rvtvcc.visibility = View.GONE
                            } else {
                                binding.shimmermakanan.stopShimmer()
                                binding.shimmermakanan.visibility = View.GONE
                                binding.txtnodata.visibility = View.GONE
                                binding.rvtvcc.visibility = View.VISIBLE

                                for (hasil in data.data!!) {
                                    notesList.add(hasil)
                                    mAdapter = TvccAdapter(notesList)
                                    binding.rvtvcc.adapter = mAdapter

                                    mAdapter.setDialog(object : TvccAdapter.Dialog {
                                        override fun onClick(
                                            position: Int,
                                            TvccModel: TvccModel
                                        ) {
                                            val gson = Gson()
                                            val noteJson = gson.toJson(TvccModel)
                                            startActivity<TvccDetailActivity>(
                                                "tvcc" to noteJson,
                                                "menu" to menu
                                            )

                                        }


                                    })
                                    mAdapter.notifyDataSetChanged()
                                }

                                binding.srcTvcc.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                                    override fun onQueryTextSubmit(p0: String?): Boolean {
                                        notesList.clear()
                                        return false
                                    }

                                    override fun onQueryTextChange(p0: String?): Boolean {
                                        search_bayarbeli(p0,notesList)
                                        return false
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

                override fun onFailure(call: Call<TvccResponse>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })

    }

    fun get_pasardesa() {
        binding.shimmermakanan.startShimmer()
        api.pasardesa(Constant.API_KEY_BACKEND)
            .enqueue(object : Callback<TvccResponse> {
                override fun onResponse(
                    call: Call<TvccResponse>,
                    response: Response<TvccResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<TvccModel>()
                            val data = response.body()
                            if (data!!.data!!.isEmpty()) {
                                binding.shimmermakanan.stopShimmer()
                                binding.shimmermakanan.visibility = View.GONE
                                binding.txtnodata.visibility = View.VISIBLE
                                binding.rvtvcc.visibility = View.GONE
                            } else {
                                binding.shimmermakanan.stopShimmer()
                                binding.shimmermakanan.visibility = View.GONE
                                binding.txtnodata.visibility = View.GONE
                                binding.rvtvcc.visibility = View.VISIBLE

                                for (hasil in data.data!!) {
                                    notesList.add(hasil)
                                    mAdapter = TvccAdapter(notesList)
                                    binding.rvtvcc.adapter = mAdapter

                                    mAdapter.setDialog(object : TvccAdapter.Dialog {
                                        override fun onClick(
                                            position: Int,
                                            TvccModel: TvccModel
                                        ) {
                                            val gson = Gson()
                                            val noteJson = gson.toJson(TvccModel)
                                            startActivity<TvccDetailActivity>(
                                                "tvcc" to noteJson,
                                                "menu" to menu
                                            )

                                        }


                                    })
                                    mAdapter.notifyDataSetChanged()
                                }

                                binding.srcTvcc.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                                    override fun onQueryTextSubmit(p0: String?): Boolean {
                                        notesList.clear()
                                        return false
                                    }

                                    override fun onQueryTextChange(p0: String?): Boolean {
                                        search_pasardesa(p0,notesList)
                                        return false
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

                override fun onFailure(call: Call<TvccResponse>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })

    }


}