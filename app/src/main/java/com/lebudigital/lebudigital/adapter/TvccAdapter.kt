package com.lebudigital.lebudigital.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.model.tvcc.TvccModel
import com.lebudigital.lebudigital.webservice.Constant
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import java.text.NumberFormat

class TvccAdapter(
    private val notesList: MutableList<TvccModel>
) : RecyclerView.Adapter<TvccAdapter.ViewHolder>() {

    //database
    private var dialog: Dialog? = null


    interface Dialog {
        fun onClick(position: Int, TvccModel: TvccModel)
    }

    fun setDialog(dialog: Dialog) {
        this.dialog = dialog
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var imgfoto: ImageView
        internal var txtnarasi: TextView
        internal var txtjudul: TextView


        init {
            imgfoto = view.findViewById(R.id.imgfoto)
            txtnarasi = view.findViewById(R.id.txtnarasi)
            txtjudul = view.findViewById(R.id.txtjudul)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_tvcc, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = notesList[position]
        Picasso.get().load(Constant.STORAGE + note.foto).centerCrop().fit().into(holder.imgfoto)
        //Subsquence batasan tampilan nama
        if (note.judul!!.length > 40) {
            holder.txtjudul.text = "${note.judul.subSequence(0, 40)}.."
        } else {
            holder.txtjudul.text = note.judul
        }

        if (note.narasi!!.length > 60) {
            holder.txtnarasi.text = "${note.narasi.subSequence(0, 60)}.."
        } else {
            holder.txtnarasi.text = note.narasi
        }



        holder.itemView.setOnClickListener {
            if (dialog != null) {
                dialog!!.onClick(holder.layoutPosition, note)
            }
        }

    }
}


