package com.lebudigital.lebudigital.adapter.kegiatandesa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.model.kegiatandesa.KegiatanDesaModel
import com.lebudigital.lebudigital.webservice.Constant
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat

class KegiatanDesaAdapter(
    private val notesList: MutableList<KegiatanDesaModel>
) : RecyclerView.Adapter<KegiatanDesaAdapter.ViewHolder>() {

    //database
    private var dialog: Dialog? = null


    interface Dialog {
        fun onClick(position: Int, KegiatanDesaModel: KegiatanDesaModel)
    }

    fun setDialog(dialog: Dialog) {
        this.dialog = dialog
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var txtanggal: TextView
        internal var txtnamakegiatan: TextView
        internal var txtkegiatanDesa: TextView


        init {
            txtanggal = view.findViewById(R.id.txtanggal)
            txtnamakegiatan = view.findViewById(R.id.txtnamakegiatan)
            txtkegiatanDesa = view.findViewById(R.id.txtkegiatanDesa)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_kegiatandesa, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = notesList[position]
        //Subsquence batasan tampilan nama
        if (note.kegiatanDesa!!.length > 13) {
            holder.txtkegiatanDesa.text = "${note.kegiatanDesa.subSequence(0, 13)}.."
        } else {
            holder.txtkegiatanDesa.text = note.kegiatanDesa
        }

        if (note.namaKegiatan!!.length > 23) {
            holder.txtnamakegiatan.text = "${note.namaKegiatan.subSequence(0, 23)}.."
        } else {
            holder.txtnamakegiatan.text = note.namaKegiatan
        }

        val sdf = SimpleDateFormat("dd MMM yyyy")
        val currentDate = sdf.format(note.tanggal)

        holder.txtanggal.text = currentDate




        holder.itemView.setOnClickListener {
            if (dialog != null) {
                dialog!!.onClick(holder.layoutPosition, note)
            }
        }

    }
}


