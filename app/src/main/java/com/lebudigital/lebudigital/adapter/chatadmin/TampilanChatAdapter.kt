package com.lebudigital.lebudigital.adapter.chatadmin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.model.chatadmin.TampilanChatModel
import com.lebudigital.lebudigital.webservice.Constant
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt

class TampilanChatAdapter(
    private val notesList: MutableList<TampilanChatModel>
) : RecyclerView.Adapter<TampilanChatAdapter.ViewHolder>() {

    //database
    private var dialog: Dialog? = null


    interface Dialog {
        fun onClick(position: Int, TampilanChatModel: TampilanChatModel)
    }

    fun setDialog(dialog: Dialog) {
        this.dialog = dialog
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var txtnama: TextView
        internal var txtchat: TextView


        init {
            txtnama = view.findViewById(R.id.txtnama)
            txtchat = view.findViewById(R.id.txtchat)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_chatadmin, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = notesList[position]
        holder.txtnama.text = note.nama
        holder.txtchat.text = note.chat
        holder.itemView.setOnClickListener {
            if (dialog != null) {
                dialog!!.onClick(holder.layoutPosition, note)
            }
        }

    }
}


