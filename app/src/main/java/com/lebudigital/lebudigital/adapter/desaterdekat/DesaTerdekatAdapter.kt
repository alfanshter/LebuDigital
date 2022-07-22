package com.lebudigital.lebudigital.adapter.desaterdekat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.model.desaterdekat.DesaTerdekatModel
import com.lebudigital.lebudigital.webservice.Constant
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt

class DesaTerdekatAdapter(
    private val notesList: MutableList<DesaTerdekatModel>
) : RecyclerView.Adapter<DesaTerdekatAdapter.ViewHolder>() {

    //database
    private var dialog: Dialog? = null


    interface Dialog {
        fun onClick(position: Int, DesaTerdekatModel: DesaTerdekatModel)
    }

    fun setDialog(dialog: Dialog) {
        this.dialog = dialog
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var txtdesa: TextView
        internal var txtjarak: TextView


        init {
            txtdesa = view.findViewById(R.id.txtdesa)
            txtjarak = view.findViewById(R.id.txtjarak)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_desaterdekat, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = notesList[position]

        holder.txtdesa.text = note.desa!!.name
        val jarak = (note.distance!! * 100).roundToInt() / 100.0
        holder.txtjarak.text = "${jarak * 1000} M"


        holder.itemView.setOnClickListener {
            if (dialog != null) {
                dialog!!.onClick(holder.layoutPosition, note)
            }
        }

    }
}


