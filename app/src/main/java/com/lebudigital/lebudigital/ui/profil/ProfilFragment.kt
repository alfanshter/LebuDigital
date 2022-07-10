package com.lebudigital.lebudigital.ui.profil

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.adapter.beritadesa.BeritaDesaAdapter
import com.lebudigital.lebudigital.databinding.FragmentProfilBinding
import com.lebudigital.lebudigital.model.auth.UsersModel
import com.lebudigital.lebudigital.model.beritadesa.BeritaDesaModel
import com.lebudigital.lebudigital.session.SessionManager
import com.lebudigital.lebudigital.ui.beranda.menu.berita.BeritaDesaDetailActivity
import com.lebudigital.lebudigital.webservice.ApiClient
import com.lebudigital.lebudigital.webservice.Constant
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilFragment : Fragment(),AnkoLogger {

    lateinit var binding : FragmentProfilBinding
    var api = ApiClient.instance()
    lateinit var sessionManager: SessionManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profil, container, false)
        binding.lifecycleOwner
        sessionManager = SessionManager(requireContext().applicationContext)

        return binding.root
    }

    fun profil(){
        api.profil(sessionManager.getiduser()!!)
            .enqueue(object : Callback<UsersModel> {
                override fun onResponse(
                    call: Call<UsersModel>,
                    response: Response<UsersModel>
                ) {
                    try {
                        if (response.isSuccessful) {
                            var data = response.body()!!
                            binding.txtnama.text = data.name
                            binding.txtalamat.text = data.alamatLengkap
                            binding.txtemail.text = data.email
                            binding.txtnik.text = data.nik
                            binding.txttelepon.text = data.telepon

                            Picasso.get().load(Constant.STORAGE+data.fotoKk).centerCrop().fit().into(binding.imgfoto)
                            Picasso.get().load(Constant.STORAGE+data.fotoKtp).centerCrop().fit().into(binding.imgfotoktp)

                        } else {
                            toast("gagal mendapatkan response")
                        }
                    } catch (e: Exception) {
                        info { "dinda ${e.message}" }
                    }
                }

                override fun onFailure(call: Call<UsersModel>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })

    }

}