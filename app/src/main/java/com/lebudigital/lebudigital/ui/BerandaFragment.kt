package com.lebudigital.lebudigital.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.databinding.FragmentBerandaBinding


class BerandaFragment : Fragment() {

    lateinit var binding: FragmentBerandaBinding

    var is_seemore = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_beranda, container, false)
        binding.lifecycleOwner

            binding.btnseemore.setOnClickListener {
                if (is_seemore ==0){
                    is_seemore = 1
                    binding.lnaktif1.visibility = View.VISIBLE
                    binding.lnaktif2.visibility = View.VISIBLE
                    binding.txtseemore.text = "See Less"

                }else{
                    is_seemore = 0
                    binding.lnaktif1.visibility = View.GONE
                    binding.lnaktif2.visibility = View.GONE
                    binding.txtseemore.text = "See More"

                }

            }



        return binding.root
    }

}