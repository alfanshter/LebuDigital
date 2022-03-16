package com.lebudigital.lebudigital.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.databinding.FragmentBerandaBinding
import com.lebudigital.lebudigital.databinding.FragmentTransaksiBinding

class TransaksiFragment : Fragment() {

    lateinit var binding: FragmentTransaksiBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transaksi, container, false)
        binding.lifecycleOwner

        return binding.root
    }

}