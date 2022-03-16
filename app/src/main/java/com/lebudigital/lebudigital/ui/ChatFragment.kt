package com.lebudigital.lebudigital.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.databinding.FragmentChatBinding
import com.lebudigital.lebudigital.databinding.FragmentTransaksiBinding

class ChatFragment : Fragment() {

    lateinit var binding: FragmentChatBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)
        binding.lifecycleOwner

        return binding.root
    }

}