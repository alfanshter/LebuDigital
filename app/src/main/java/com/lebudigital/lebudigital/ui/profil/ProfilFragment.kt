package com.lebudigital.lebudigital.ui.profil

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.lebudigital.lebudigital.MainActivity
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.adapter.beritadesa.BeritaDesaAdapter
import com.lebudigital.lebudigital.auth.LoginActivity
import com.lebudigital.lebudigital.databinding.FragmentProfilBinding
import com.lebudigital.lebudigital.model.beritadesa.BeritaDesaModel
import com.lebudigital.lebudigital.model.users.ProfilResponse
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
            .enqueue(object : Callback<ProfilResponse> {
                override fun onResponse(
                    call: Call<ProfilResponse>,
                    response: Response<ProfilResponse>
                ) {

                        if (response.isSuccessful) {

                            var data = response.body()!!
                            if (data.status == 1){
                                val alamat_lengkap = "Desa ${data.profil!!.desa!!.name} Kec. ${data.profil!!.kecamatan!!.name} ${data.profil!!.kabupaten!!.name} Prov. ${data.profil!!.provinsi!!.name} "

                                sessionProfilManager.setNama(data.profil!!.name!!)
                                sessionProfilManager.setAlamat(alamat_lengkap)
                                sessionProfilManager.setTelepon(data.profil!!.telepon!!)
                                sessionProfilManager.setEmail(data.profil!!.email!!)

                                binding.txtnama.text = data.profil!!.name
                                binding.txtalamat.text = alamat_lengkap.toLowerCase()
                                binding.txtemail.text = data.profil!!.email
                                if (data.profil!!.nik!=null){
                                    sessionProfilManager.setNik(data.profil!!.nik!!)
                                    binding.txtnik.text = data.profil!!.nik.toString()
                                }else{
                                    binding.txtnik.text = "Tambahkan NIK anda"
                                }
                                binding.txttelepon.text = data.profil!!.telepon

                                Picasso.get().load(Constant.STORAGE+data.profil!!.fotoKk).centerCrop().fit().into(binding.imgkk)
                                Picasso.get().load(Constant.STORAGE+data.profil!!.fotoKtp).centerCrop().fit().into(binding.imgfotoktp)
                                Picasso.get().load(Constant.STORAGE+data.profil!!.foto_akta).centerCrop().fit().into(binding.imgakta)
                                Picasso.get().load(Constant.STORAGE+data.profil!!.foto).centerCrop().fit().into(binding.imgfoto)

                                binding.btnupdate.setOnClickListener {
                                    val gson = Gson()
                                    val noteJson = gson.toJson(data)
                                    startActivity<UpdateProfilActivity>("profil" to noteJson)

                                }
                            }
                            else if (data.status == 2){
                                sessionManager.setLogin(false)
                                sessionManager.setLoginadmin(false)
                                sessionProfilManager.setAlamat("")
                                sessionProfilManager.setNik("")
                                sessionProfilManager.setTelepon("")
                                sessionProfilManager.setNama("")
                                sessionProfilManager.setEmail("")
                                toast("Akun anda telah dihapus")
                                startActivity<LoginActivity>()
                                activity!!.finish()
                                MainActivity.activity.finish()
                            }

                        } else {
                            toast("gagal mendapatkan response")
                        }
                }

                override fun onFailure(call: Call<ProfilResponse>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })

    }

}