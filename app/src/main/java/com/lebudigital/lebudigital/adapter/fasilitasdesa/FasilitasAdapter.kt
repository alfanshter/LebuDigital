package com.lebudigital.lebudigital.adapter.fasilitasdesa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.model.fasilitas.FasilitasModel
import com.lebudigital.lebudigital.webservice.Constant
import com.squareup.picasso.Picasso

class FasilitasAdapter(
    private val notesList: MutableList<FasilitasModel>
) : RecyclerView.Adapter<FasilitasAdapter.ViewHolder>() {

    //database
    private var dialog: Dialog? = null


    interface Dialog {
        fun onClick(position: Int, FasilitasModel: FasilitasModel)
    }

    fun setDialog(dialog: Dialog) {
        this.dialog = dialog
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var imgfoto: ImageView
        internal var txtdeskripsi: TextView
        internal var txtjudul: TextView


        init {
            imgfoto = view.findViewById(R.id.imgfoto)
            txtdeskripsi = view.findViewById(R.id.txtdeskripsi)
            txtjudul = view.findViewById(R.id.txtjudul)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_fasilitas, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = notesList[position]
        Picasso.get().load(Constant.STORAGE + note.foto).centerCrop().fit().into(holder.imgfoto)
        //Subsquence batasan tampilan nama
        if (note.fasilitas!!.length > 13) {
            holder.txtjudul.text = "${note.fasilitas.subSequence(0, 13)}.."
        } else {
            holder.txtjudul.text = note.fasilitas
        }

        if (note.deskripsi!!.length > 23) {
            holder.txtdeskripsi.text = "${note.deskripsi.subSequence(0, 23)}.."
        } else {
            holder.txtdeskripsi.text = note.deskripsi
        }



        holder.itemView.setOnClickListener {
            if (dialog != null) {
                dialog!!.onClick(holder.layoutPosition, note)
            }
        }

    }
}


