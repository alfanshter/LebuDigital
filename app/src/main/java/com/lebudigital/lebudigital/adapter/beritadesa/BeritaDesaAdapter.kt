package com.lebudigital.lebudigital.adapter.beritadesa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.model.beritadesa.BeritaDesaModel

import com.lebudigital.lebudigital.webservice.Constant
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

class BeritaDesaAdapter(
    private val notesList: MutableList<BeritaDesaModel>
) : RecyclerView.Adapter<BeritaDesaAdapter.ViewHolder>() {

    //database
    private var dialog: Dialog? = null


    interface Dialog {
        fun onClick(position: Int, BeritaDesaModel: BeritaDesaModel)
    }

    fun setDialog(dialog: Dialog) {
        this.dialog = dialog
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var imgfoto: ImageView
        internal var txttanggalterbit: TextView
        internal var txtjudul: TextView
        internal var txtbaca: TextView


        init {
            imgfoto = view.findViewById(R.id.imgfoto)
            txttanggalterbit = view.findViewById(R.id.txttanggalterbit)
            txtjudul = view.findViewById(R.id.txtjudul)
            txtbaca = view.findViewById(R.id.txtbaca)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_beritadesa, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = notesList[position]
        Picasso.get().load(Constant.STORAGE + note.foto).centerCrop().fit().into(holder.imgfoto)
        //Subsquence batasan tampilan nama
        if (note.judul!!.length > 25) {
            holder.txtjudul.text = "${note.judul.subSequence(0, 25)}.."
        } else {
            holder.txtjudul.text = note.judul
        }

        val sdf = SimpleDateFormat("dd MMM yyyy")
        val currentDate = sdf.format(note.tanggalTerbit)

        holder.txttanggalterbit.text = currentDate




        holder.itemView.setOnClickListener {
            if (dialog != null) {
                dialog!!.onClick(holder.layoutPosition, note)
            }
        }

    }
}


