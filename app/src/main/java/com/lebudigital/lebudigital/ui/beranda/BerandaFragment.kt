package com.lebudigital.lebudigital.ui.beranda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.databinding.FragmentBerandaBinding
import com.lebudigital.lebudigital.ui.beranda.menu.berita.BeritaDesaActivity
import com.lebudigital.lebudigital.ui.beranda.menu.berita.budaya.BudayaLokalActivity
import com.lebudigital.lebudigital.ui.beranda.menu.grubinformasi.desaterdekat.DesaTerdekatActivity
import com.lebudigital.lebudigital.ui.beranda.menu.grubinformasi.fasilitasdesa.FasilitasDesaActivity
import com.lebudigital.lebudigital.ui.beranda.menu.grubinformasi.geospasial.GeospasialActivity
import com.lebudigital.lebudigital.ui.beranda.menu.grubberita.kegiatandesa.KegiatanDesaActivity
import com.lebudigital.lebudigital.ui.beranda.menu.grubberita.pelatihan.PelatihanActivity
import com.lebudigital.lebudigital.ui.beranda.menu.grubinformasi.profildesa.ProfilDesaActivity
import com.lebudigital.lebudigital.ui.beranda.menu.tvcc.TvccActivity
import org.jetbrains.anko.support.v4.startActivity


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

        binding.btntvcc.setOnClickListener {
            startActivity<TvccActivity>(
                "menu" to "TVCC"
            )
        }

        binding.btnlada.setOnClickListener {
            startActivity<TvccActivity>(
                "menu" to "LADA"
            )
        }


        binding.btnbayarbeli.setOnClickListener {
            startActivity<TvccActivity>(
                "menu" to "BAYAR/BELI"
            )
        }

        binding.btnpasardesa.setOnClickListener {
            startActivity<TvccActivity>(
                "menu" to "PASAR DESA"
            )
        }

        binding.btnberitadesa.setOnClickListener {
            startActivity<BeritaDesaActivity>()
        }

        binding.btnprofildesa.setOnClickListener {
            startActivity<ProfilDesaActivity>()
        }

        binding.btnfasilitasdesa.setOnClickListener {
            startActivity<FasilitasDesaActivity>()
        }

        binding.btnbudayalokal.setOnClickListener {
            startActivity<BudayaLokalActivity>()
        }

        binding.btngeospasial.setOnClickListener {
            startActivity<GeospasialActivity>()
        }
        binding.btndesaterdekat.setOnClickListener {
            startActivity<DesaTerdekatActivity>()
        }
        binding.btnkegiatandesa.setOnClickListener {
            startActivity<KegiatanDesaActivity>()
        }

        binding.btnpelatihan.setOnClickListener {
            startActivity<PelatihanActivity>()
        }




        return binding.root
    }

}