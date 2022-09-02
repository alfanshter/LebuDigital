package com.lebudigital.lebudigital.adapter.budayalokal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.model.budayalokal.BudayaLokalModel
import com.lebudigital.lebudigital.webservice.Constant
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import java.text.NumberFormat

class BudayaLokalAdapter(
    private val notesList: MutableList<BudayaLokalModel>
) : RecyclerView.Adapter<BudayaLokalAdapter.ViewHolder>() {

    //database
    private var dialog: Dialog? = null


    interface Dialog {
        fun onClick(position: Int, BudayaLokalModel: BudayaLokalModel)
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
        if (note.cagarBudaya!!.length > 13) {
            holder.txtjudul.text = "${note.judul!!.subSequence(0, 13)}.."
        } else {
            holder.txtjudul.text = note.judul
        }

        if (note.judul!!.length > 23) {
            holder.txtdeskripsi.text = "${note.cagarBudaya.subSequence(0, 23)}.."
        } else {
            holder.txtdeskripsi.text = note.cagarBudaya
        }



        holder.itemView.setOnClickListener {
            if (dialog != null) {
                dialog!!.onClick(holder.layoutPosition, note)
            }
        }

    }
}


