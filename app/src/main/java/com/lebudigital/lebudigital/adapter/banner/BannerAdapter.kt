package com.lebudigital.lebudigital.adapter.banner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.model.banner.BannerModel
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso

class BannerAdapter() :
    SliderViewAdapter<BannerAdapter.VH>() {
    private var mSliderItems = ArrayList<String>()
    private var mSliderModel = mutableListOf<BannerModel>()
    private var dialog: Dialog? = null
    fun setDialog(dialog: Dialog) {
        this.dialog = dialog
    }

    interface Dialog {
        fun onClick(
            position: Int,
            note: BannerModel
        )
    }
    fun renewItems(sliderItems: ArrayList<String>,slidermodel : MutableList<BannerModel>) {
        mSliderItems = sliderItems
        mSliderModel= slidermodel
        notifyDataSetChanged()
    }
    fun addItem(sliderItem: String) {
        mSliderItems.add(sliderItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): VH {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.sliderberita, null)
        return VH(inflate)
    }

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        //load image into view
        Picasso.get().load(mSliderItems[position]).fit().centerCrop().into(viewHolder.imageView)
        viewHolder.itemView.setOnClickListener {
            if (dialog!=null){
                dialog!!.onClick(position,mSliderModel[position])
            }
        }
    }

    override fun getCount(): Int {
        return mSliderItems.size
    }

    inner class VH(itemView: View) : ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.imageSlider)

    }
}
