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
import com.lebudigital.lebudigital.auth.LoginActivity
import com.lebudigital.lebudigital.databinding.FragmentProfilBinding
import com.lebudigital.lebudigital.model.beritadesa.BeritaDesaModel
import com.lebudigital.lebudigital.model.users.UsersResponse
import com.lebudigital.lebudigital.session.SessionManager
import com.lebudigital.lebudigital.session.SessionProfilManager
import com.lebudigital.lebudigital.ui.beranda.menu.berita.BeritaDesaDetailActivity
import com.lebudigital.lebudigital.webservice.ApiClient
import com.lebudigital.lebudigital.webservice.Constant
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilFragment : Fragment(),AnkoLogger {

    lateinit var binding : FragmentProfilBinding
    var api = ApiClient.instance()
    lateinit var sessionManager: SessionManager
    lateinit var sessionProfilManager: SessionProfilManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profil, container, false)
        binding.lifecycleOwner
        sessionManager = SessionManager(requireContext().applicationContext)
        sessionProfilManager = SessionProfilManager(requireContext().applicationContext)
        binding.btnlogout.setOnClickListener {
            sessionManager.setiduser(0)
            sessionManager.setLogin(false)
            startActivity<LoginActivity>()
            activity!!.finish()
        }

        binding.txtnama.text = sessionProfilManager.getNama()
        binding.txtalamat.text = sessionProfilManager.getAlamat()!!.toLowerCase()
        binding.txtnik.text = sessionProfilManager.getNik()
        binding.txttelepon.text = sessionProfilManager.getTelepon()
        binding.txtemail.text = sessionProfilManager.getEmail()


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        profil()
    }
    fun profil(){
        api.users(sessionManager.getiduser()!!)
            .enqueue(object : Callback<UsersResponse> {
                override fun onResponse(
                    call: Call<UsersResponse>,
                    response: Response<UsersResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            var data = response.body()!!
                            val alamat_lengkap = "Desa ${data.desa!!.name} Kec. ${data.kecamatan!!.name} ${data.kabupaten!!.name} Prov. ${data.provinsi!!.name} "

                            sessionProfilManager.setNama(data.name!!)
                            sessionProfilManager.setNik(data.nik!!)
                            sessionProfilManager.setAlamat(alamat_lengkap)
                            sessionProfilManager.setTelepon(data.telepon!!)
                            sessionProfilManager.setEmail(data.email!!)

                            binding.txtnama.text = data.name
                            binding.txtalamat.text = alamat_lengkap.toLowerCase()
                            binding.txtemail.text = data.email
                            if (data.nik!=null){
                                binding.txtnik.text = data.nik.toString()
                            }else{
                                binding.txtnik.text = "Tambahkan NIK anda"
                            }
                            binding.txttelepon.text = data.telepon

                            Picasso.get().load(Constant.STORAGE+data.fotoKk).centerCrop().fit().into(binding.imgkk)
                            Picasso.get().load(Constant.STORAGE+data.fotoKtp).centerCrop().fit().into(binding.imgfotoktp)
                            Picasso.get().load(Constant.STORAGE+data.foto_akta).centerCrop().fit().into(binding.imgakta)
                            Picasso.get().load(Constant.STORAGE+data.foto).centerCrop().fit().into(binding.imgfoto)

                            binding.btnupdate.setOnClickListener {
                                val gson = Gson()
                                val noteJson = gson.toJson(data)
                                startActivity<UpdateProfilActivity>("profil" to noteJson)

                            }
                        } else {
                            toast("gagal mendapatkan response")
                        }
                    } catch (e: Exception) {
                        info { "dinda ${e.message}" }
                    }
                }

                override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })

    }

}