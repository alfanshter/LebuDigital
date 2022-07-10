package com.lebudigital.lebudigital.adapter.pelatihan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.model.pelatihan.PelatihanMinggu

import com.lebudigital.lebudigital.webservice.Constant
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

class PelatihanDesaAdapter(
    private val notesList: MutableList<PelatihanMinggu>
) : RecyclerView.Adapter<PelatihanDesaAdapter.ViewHolder>() {

    //database
    private var dialog: Dialog? = null


    interface Dialog {
        fun onClick(position: Int, PelatihanMinggu: PelatihanMinggu)
    }

    fun setDialog(dialog: Dialog) {
        this.dialog = dialog
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var imgfoto: ImageView
        internal var txttanggal: TextView
        internal var txtjudul: TextView
        internal var txtalamat: TextView


        init {
            imgfoto = view.findViewById(R.id.imgfoto)
            txttanggal = view.findViewById(R.id.txttanggal)
            txtjudul = view.findViewById(R.id.txtjudul)
            txtalamat = view.findViewById(R.id.txtalamat)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_pelatihan, parent, false)

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
        val currentDate = sdf.format(note.tanggal)

        holder.txttanggal.text = currentDate
        if (note.lokasiKegiatan!!.length > 25) {
            holder.txtalamat.text = "${note.lokasiKegiatan.subSequence(0, 25)}.."
        } else {
            holder.txtalamat.text = note.lokasiKegiatan
        }



        holder.itemView.setOnClickListener {
            if (dialog != null) {
                dialog!!.onClick(holder.layoutPosition, note)
            }
        }

    }
}


