package com.lebudigital.lebudigital.adapter.profildesa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.model.profildesa.DataStatistikModel

class DataStatistikAdapter(
    private val notesList: MutableList<DataStatistikModel>
) : RecyclerView.Adapter<DataStatistikAdapter.ViewHolder>() {

    //database
    private var dialog: Dialog? = null


    interface Dialog {
        fun onClick(position: Int, DataStatistikModel: DataStatistikModel)
    }

    fun setDialog(dialog: Dialog) {
        this.dialog = dialog
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var txtjenis: TextView
        internal var txtjumlah: TextView


        init {
            txtjenis = view.findViewById(R.id.txtjenis)
            txtjumlah = view.findViewById(R.id.txtjumlah)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_datastatistik, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = notesList[position]

        holder.txtjenis.text = note.jenis
        holder.txtjumlah.text = note.jumlah.toString()



        holder.itemView.setOnClickListener {
            if (dialog != null) {
                dialog!!.onClick(holder.layoutPosition, note)
            }
        }

    }
}


