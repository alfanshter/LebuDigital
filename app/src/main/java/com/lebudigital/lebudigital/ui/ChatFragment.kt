package com.lebudigital.lebudigital.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.adapter.chatadmin.ChatAdminAdapter
import com.lebudigital.lebudigital.adapter.chatadmin.TampilanChatAdapter
import com.lebudigital.lebudigital.databinding.FragmentChatBinding
import com.lebudigital.lebudigital.model.beritadesa.BeritaDesaModel
import com.lebudigital.lebudigital.model.beritadesa.BeritaDesaResponse
import com.lebudigital.lebudigital.model.chatadmin.ChatAdminModel
import com.lebudigital.lebudigital.model.chatadmin.TampilanChatModel
import com.lebudigital.lebudigital.session.SessionManager
import com.lebudigital.lebudigital.ui.beranda.menu.berita.BeritaDesaDetailActivity
import com.lebudigital.lebudigital.ui.chat.ChatAdminActivity
import com.lebudigital.lebudigital.ui.chat.TambahChatActivity
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

class ChatFragment : Fragment(),AnkoLogger {

    lateinit var binding: FragmentChatBinding
    lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var mAdapter: ChatAdminAdapter
    private lateinit var mAdaptertampilan: TampilanChatAdapter
    lateinit var sessionManager: SessionManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)
        binding.lifecycleOwner
        firebaseDatabase =
            FirebaseDatabase.getInstance("https://lebudigital-c0249-default-rtdb.asia-southeast1.firebasedatabase.app/")
        sessionManager = SessionManager(requireContext().applicationContext)
        get_chat_admin()
        gettampilanchat()
        binding.btnchat.setOnClickListener {
            startActivity<TambahChatActivity>()
        }


        return binding.root
    }

    fun get_chat_admin() {
        binding.rvadmin.layoutManager = LinearLayoutManager(requireContext().applicationContext)
        binding.rvadmin.setHasFixedSize(true)
        (binding.rvadmin.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL

        firebaseDatabase.reference.child("chat").child("admin")
            .child("provinsi")
            .child(sessionManager.getprovince_id().toString())
            .child(sessionManager.getregencie_id().toString())
            .child(sessionManager.getdistrict_id().toString())
            .child(sessionManager.getvillage_id().toString())
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val notesList = mutableListOf<ChatAdminModel>()

                    for (data in snapshot.children){
                        val body = data.getValue(ChatAdminModel::class.java)
                        notesList.add(body!!)
                        mAdapter = ChatAdminAdapter(notesList)
                        binding.rvadmin.adapter = mAdapter

                        mAdapter.setDialog(object : ChatAdminAdapter.Dialog {
                            
                            override fun onClick(position: Int, ChatAdminModel: ChatAdminModel) {
                                startActivity<ChatAdminActivity>(
                                    "nama_user" to "Admin",
                                    "pelanggan_uid" to ChatAdminModel.admin_id
                                )
                            }


                        })


                        mAdapter.notifyDataSetChanged()
                    }


                }

                override fun onCancelled(error: DatabaseError) {
                info { "dinda db ${error.message}" }
                }

            })

    }


    fun gettampilanchat() {
        binding.rvuser.layoutManager = LinearLayoutManager(requireContext().applicationContext)
        binding.rvuser.setHasFixedSize(true)
        (binding.rvuser.layoutManager as LinearLayoutManager).orientation =
            LinearLayoutManager.VERTICAL

        firebaseDatabase.reference.child("chat").child("tampildepan")
            .child(sessionManager.getiduser().toString())
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val notesList = mutableListOf<TampilanChatModel>()
                    if (snapshot.exists()){
                        binding.shimmermakanan.visibility = View.GONE
                        binding.shimmermakanan.stopShimmer()
                        binding.rvuser.visibility = View.VISIBLE
                        for (data in snapshot.children){
                            info { "dinda $data" }
                            val body = data.getValue(TampilanChatModel::class.java)
                            notesList.add(body!!)
                            mAdaptertampilan = TampilanChatAdapter(notesList)
                            binding.rvuser.adapter = mAdaptertampilan

                            mAdaptertampilan.setDialog(object : TampilanChatAdapter.Dialog {

                                override fun onClick(position: Int, TampilanChatModel: TampilanChatModel) {
                                    startActivity<ChatAdminActivity>(
                                        "nama_user" to TampilanChatModel.nama,
                                        "pelanggan_uid" to TampilanChatModel.uid
                                    )
                                }


                            })


                            mAdaptertampilan.notifyDataSetChanged()
                        }

                    }else{
                        binding.shimmermakanan.visibility = View.GONE
                        binding.shimmermakanan.stopShimmer()
                        binding.rvuser.visibility = View.GONE
                    }


                }

                override fun onCancelled(error: DatabaseError) {
                    info { "dinda db ${error.message}" }
                }

            })

    }

}