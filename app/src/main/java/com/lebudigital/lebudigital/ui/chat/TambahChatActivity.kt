package com.lebudigital.lebudigital.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.adapter.chatadmin.RiwayatUserAdapter
import com.lebudigital.lebudigital.adapter.pencarianuser.PencarianUserAdapter
import com.lebudigital.lebudigital.databinding.ActivityTambahChatBinding
import com.lebudigital.lebudigital.model.chatadmin.RiwayatChatModel
import com.lebudigital.lebudigital.model.chatpencarian.PencarianUserModel
import com.lebudigital.lebudigital.model.chatpencarian.PencarianUserResponse
import com.lebudigital.lebudigital.session.SessionManager
import com.lebudigital.lebudigital.webservice.ApiClient
import com.lebudigital.lebudigital.webservice.Constant
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahChatActivity : AppCompatActivity(), AnkoLogger {
    var api = ApiClient.instance()
    private lateinit var mAdapter: PencarianUserAdapter
    private lateinit var mAdapterUser: RiwayatUserAdapter
    lateinit var binding: ActivityTambahChatBinding
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tambah_chat)
        binding.lifecycleOwner = this
        firebaseDatabase = FirebaseDatabase.getInstance(Constant.firebase_realtime)
        sessionManager = SessionManager(this)
        binding.srccariemail.queryHint = "Cari email pengguna"
        binding.rvkontak.layoutManager = LinearLayoutManager(this)
        binding.rvkontak.setHasFixedSize(true)
        (binding.rvkontak.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL

        binding.btnback.setOnClickListener {
            finish()
        }
        getuser()

    }

    fun getuser() {
        var getdata = firebaseDatabase.reference.child("chat")
            .child("riwayat")
            .child(sessionManager.getiduser().toString())
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val notesList = mutableListOf<RiwayatChatModel>()
                    for (hasil in snapshot.children) {
                        var usermodel = hasil.getValue(RiwayatChatModel::class.java)
                        notesList.add(usermodel!!)
                        mAdapterUser = RiwayatUserAdapter(notesList)
                        binding.rvkontak.adapter = mAdapterUser

                        mAdapterUser.setDialog(object : RiwayatUserAdapter.Dialog {
                            override fun onClick(position: Int, riwayatChatModel: RiwayatChatModel) {
                                startActivity<ChatAdminActivity>(
                                    "nama_user" to riwayatChatModel.nama,
                                    "pelanggan_uid" to riwayatChatModel.user_id)
                            }

                        })
                        mAdapterUser.notifyDataSetChanged()
                    }

                    binding.srccariemail.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(p0: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(p0: String?): Boolean {
                            getsearch(p0)
                            return false
                        }

                    })


                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
     

    }

    private fun getsearch(searchTerm: String?) {
        if (!TextUtils.isEmpty(searchTerm)) {
            val serchtext: String =
                searchTerm!!.substring(0, 1).toUpperCase() + searchTerm.substring(1)

            api.search_user(serchtext).enqueue(object : Callback<PencarianUserResponse> {
                override fun onResponse(
                    call: Call<PencarianUserResponse>,
                    response: Response<PencarianUserResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<PencarianUserModel>()
                            val data = response.body()
                            for (hasil in data!!.data!!) {
                                notesList.add(hasil)
                                mAdapter = PencarianUserAdapter(notesList)
                                binding.rvkontak.adapter = mAdapter

                                mAdapter.setDialog(object : PencarianUserAdapter.Dialog {
                                    override fun onClick(
                                        position: Int,
                                        PencarianUserModel: PencarianUserModel
                                    ) {
                                        startActivity<ChatAdminActivity>(
                                            "nama_user" to PencarianUserModel.name,
                                            "pelanggan_uid" to PencarianUserModel.id.toString(),
                                            "pencarian" to "cari"
                                        )
                                    }

                                })
                                mAdapter.notifyDataSetChanged()
                            }

                            binding.srccariemail.setOnQueryTextListener(object :
                                SearchView.OnQueryTextListener {
                                override fun onQueryTextSubmit(p0: String?): Boolean {
                                    notesList.clear()
                                    return false
                                }

                                override fun onQueryTextChange(p0: String?): Boolean {
                                    getsearch(p0)
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

                override fun onFailure(call: Call<PencarianUserResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })


            val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(
                this,
                RecyclerView.VERTICAL,
                false
            )

        }
    }

}