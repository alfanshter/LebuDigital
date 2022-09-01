package com.lebudigital.lebudigital.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.*
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.databinding.ActivityChatAdminBinding
import com.lebudigital.lebudigital.model.ChatMessage
import com.lebudigital.lebudigital.session.SessionManager
import com.lebudigital.lebudigital.session.SessionProfilManager
import com.lebudigital.lebudigital.webservice.Constant
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*


class ChatAdminActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatAdminBinding

    val adapter = GroupAdapter<ViewHolder>()
    var pelanggan_uid: String? = null
    var nama_user: String? = null
    var pencarian: String? = null

    lateinit var reference: FirebaseDatabase
    lateinit var sessionManager: SessionManager
    lateinit var sessionProfilManager: SessionProfilManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_admin)
        binding.lifecycleOwner = this
        sessionManager = SessionManager(this)
        sessionProfilManager = SessionProfilManager(this)


        val bundle: Bundle? = intent.extras
        nama_user = bundle!!.getString("nama_user")
        pelanggan_uid = bundle.getString("pelanggan_uid")
        pencarian = bundle.getString("pencarian")

        if (pencarian != null) {
            tambahriwayat()
        }


        binding.swiperefresh.setColorSchemeColors(
            ContextCompat.getColor(
                this,
                R.color.merah
            )
        )

        binding.recyclerviewChatLog.adapter = adapter
        reference = FirebaseDatabase.getInstance(Constant.firebase_realtime)
        binding.txtnama.text = nama_user.toString()

        ambildata()

        binding.btnback.setOnClickListener {
            finish()
        }
        binding.btnkirimPesan.setOnClickListener {
            performSendMessagebackend()

        }

//        var ubahstatuspesan = reference.getReference("chat").child("statusnotif").child(userID.toString()).child("status").setValue(false)

    }

    private fun tambahriwayat() {

        val usermap: MutableMap<String, Any?> = HashMap()
        usermap["user_id"] = pelanggan_uid
        usermap["nama"] = nama_user


        val addriwayat =
            FirebaseDatabase.getInstance(Constant.firebase_realtime).reference
                .child("chat")
                .child("riwayat")
                .child(sessionManager.getiduser().toString())
                .child(pelanggan_uid.toString())
                .setValue(usermap)

    }


    private fun ambildata() {
        binding.swiperefresh.isEnabled = true
        binding.swiperefresh.isRefreshing = true

        val fromId = sessionManager.getiduser() ?: return
        val toId = pelanggan_uid
        if (nama_user == "Admin"){

        }else{

        }
        var path_ref = ""
        if (nama_user =="Admin"){
             path_ref = "/chat/pesan/admin/user-messages/$fromId/$toId"
        }else{
             path_ref = "/chat/pesan/user-messages/$fromId/$toId"

        }
        val ref = FirebaseDatabase.getInstance(Constant.firebase_realtime)
            .getReference(path_ref)

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (!dataSnapshot.hasChildren()) {
                    binding.swiperefresh.isRefreshing = false
                    binding.swiperefresh.isEnabled = false
                }
            }
        })

        ref.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                dataSnapshot.getValue(ChatMessage::class.java)?.let {
                    if (it.fromId == sessionManager.getiduser().toString()) {
                        adapter.add(ChatToItem(it.text, it.timestamp))

                    } else {
                        adapter.add(ChatFromItem(it.text, it.timestamp))

                    }
                }
                binding.recyclerviewChatLog.scrollToPosition(adapter.itemCount - 1)
                binding.swiperefresh.isRefreshing = false
                binding.swiperefresh.isEnabled = false
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
            }

        })
    }

    private fun performSendMessagebackend() {
        val text = binding.edittextChatLog.text.toString()
        if (text.isEmpty()) {
            Toast.makeText(this, "Message cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        val fromId = sessionManager.getiduser() ?: return
        val toId = pelanggan_uid.toString()

        var path_ref = ""
        if (nama_user =="Admin"){
            path_ref = "/chat/pesan/admin/user-messages/"
        }else{
            path_ref = "/chat/pesan/user-messages/"

        }
        //kirim tampildepan
        if (nama_user!="Admin"){
            val usermap: MutableMap<String, Any?> = HashMap()
            usermap["uid"] = pelanggan_uid
            usermap["nama"] = nama_user
            usermap["chat"] = text

            val tampildepan =
                FirebaseDatabase.getInstance(Constant.firebase_realtime)
                    .getReference("chat/tampildepan/${sessionManager.getiduser()}/$pelanggan_uid")
                    .setValue(usermap)

        }

        val reference =
            FirebaseDatabase.getInstance(Constant.firebase_realtime)
                .getReference("$path_ref${sessionManager.getiduser()}/$pelanggan_uid")
                .push()
        val toReference =
            FirebaseDatabase.getInstance(Constant.firebase_realtime)
                .getReference("$path_ref$pelanggan_uid/${sessionManager.getiduser()}")
                .push()

        val chatMessage =
            ChatMessage(
                reference.key!!,
                text,
                fromId.toString(),
                toId,
                System.currentTimeMillis() / 1000,
                sessionProfilManager.getNama().toString(),
                1
            )

        reference.setValue(chatMessage)
            .addOnSuccessListener {
                binding.edittextChatLog.text!!.clear()
                binding.recyclerviewChatLog.smoothScrollToPosition(adapter.itemCount - 1)
            }

        toReference.setValue(chatMessage)


        var path_notif = ""
        if (nama_user =="Admin"){
            path_notif = "/chat/pesan/admin/statusnotif/"
        }else{
            path_notif = "/chat/pesan/statusnotif/"

        }
        val latestMessageRef =
            FirebaseDatabase.getInstance(Constant.firebase_realtime)
                .getReference("$path_notif$pelanggan_uid")
        latestMessageRef.child("status").setValue(true)

        var path_latest = ""
        if (nama_user =="Admin"){
            path_latest = "/chat/pesan/admin/latest-messages/"
        }else{
            path_latest = "/chat/pesan/latest-messages/"
        }
        val latestMessageToRef = FirebaseDatabase.getInstance(Constant.firebase_realtime)
            .getReference("$path_latest$pelanggan_uid/${sessionManager.getiduser()}")
        latestMessageToRef.setValue(chatMessage)

    }


}

class ChatFromItem(val text: String, val timestamp: Long) : Item<ViewHolder>() {

    override fun bind(viewHolder: ViewHolder, position: Int) {


        viewHolder.itemView.txt_chatmekanik.text = text
    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }

}


class ChatToItem(val text: String, val timestamp: Long) : Item<ViewHolder>() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.txt_chatuser.text = text

    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }

}
