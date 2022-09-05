package com.lebudigital.lebudigital.ui.beranda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.lebudigital.lebudigital.MainActivity
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.adapter.banner.BannerAdapter
import com.lebudigital.lebudigital.adapter.beritadesa.BeritaDesaPopular
import com.lebudigital.lebudigital.adapter.profildesa.DataStatistikAdapter
import com.lebudigital.lebudigital.auth.LoginActivity
import com.lebudigital.lebudigital.databinding.FragmentBerandaBinding
import com.lebudigital.lebudigital.model.banner.BannerModel
import com.lebudigital.lebudigital.model.banner.BannerResponse
import com.lebudigital.lebudigital.model.beritadesa.BeritaDesaModel
import com.lebudigital.lebudigital.model.beritadesa.BeritaDesaResponse
import com.lebudigital.lebudigital.model.profildesa.DataStatistikModel
import com.lebudigital.lebudigital.model.profildesa.GambarDesaModel
import com.lebudigital.lebudigital.model.profildesa.ProfilResponse
import com.lebudigital.lebudigital.session.SessionManager
import com.lebudigital.lebudigital.session.SessionProfilManager
import com.lebudigital.lebudigital.ui.beranda.banner.BannerActivity
import com.lebudigital.lebudigital.ui.beranda.menu.berita.BeritaDesaActivity
import com.lebudigital.lebudigital.ui.beranda.menu.berita.BeritaDesaDetailActivity
import com.lebudigital.lebudigital.ui.beranda.menu.berita.budaya.BudayaLokalActivity
import com.lebudigital.lebudigital.ui.beranda.menu.grubinformasi.desaterdekat.DesaTerdekatActivity
import com.lebudigital.lebudigital.ui.beranda.menu.grubinformasi.fasilitasdesa.FasilitasDesaActivity
import com.lebudigital.lebudigital.ui.beranda.menu.grubinformasi.geospasial.GeospasialActivity
import com.lebudigital.lebudigital.ui.beranda.menu.grubberita.kegiatandesa.KegiatanDesaActivity
import com.lebudigital.lebudigital.ui.beranda.menu.grubberita.pelatihan.PelatihanActivity
import com.lebudigital.lebudigital.ui.beranda.menu.grubinformasi.profildesa.ProfilDesaActivity
import com.lebudigital.lebudigital.ui.beranda.menu.tvcc.TvccActivity
import com.lebudigital.lebudigital.webservice.ApiClient
import com.lebudigital.lebudigital.webservice.Constant
import com.smarteist.autoimageslider.SliderView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class BerandaFragment : Fragment(),AnkoLogger {

    lateinit var binding: FragmentBerandaBinding

    var is_seemore = 0
    var api = ApiClient.instance()
    lateinit var sessionManager : SessionManager
    lateinit var sessionProfilManager: SessionProfilManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_beranda, container, false)
        binding.lifecycleOwner
        sessionManager = SessionManager(requireContext().applicationContext)
        sessionProfilManager = SessionProfilManager(requireContext().applicationContext)

        val sdf = SimpleDateFormat("E, dd LLLL yyyy")
        val currentDate = sdf.format(Date())
        binding.txttanggal.text = currentDate

        binding.txtnama.text = "Hi , ${sessionProfilManager.getNama()}"
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

        getbanner()
        get_beritadesa_popular()


        return binding.root
    }

    fun get_beritadesa_popular() {

        var slidermodel = mutableListOf<BeritaDesaModel>()
        binding.shimmermakanan.startShimmer()
        api.beritadesa_popular(Constant.API_KEY_BACKEND, sessionManager.getiduser()!!)
            .enqueue(object : Callback<BeritaDesaResponse> {
                override fun onResponse(
                    call: Call<BeritaDesaResponse>,
                    response: Response<BeritaDesaResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val notesList = mutableListOf<BeritaDesaModel>()
                            val data = response.body()
                            if (data!!.data!!.isEmpty()) {
                                binding.imageSlider.visibility = View.GONE
                                binding.shimmermakanan.visibility = View.GONE
                                binding.shimmermakanan.stopShimmer()
                                binding.imgkosong.visibility = View.VISIBLE
                            } else {
                                binding.imageSlider.visibility = View.VISIBLE
                                binding.shimmermakanan.visibility = View.GONE
                                binding.shimmermakanan.stopShimmer()
                                binding.imgkosong.visibility = View.GONE
                                val imageList: ArrayList<String> = ArrayList()

                                for (popular in data.data!!){
                                    slidermodel.add(popular)
                                    //slider
                                    imageList.add(Constant.STORAGE + popular.foto!!)

                                }
                                setImageInSlider(imageList, binding.imageSlider, slidermodel)



                            }

                        } else {
                            toast("gagal mendapatkan response")
                        }
                    } catch (e: Exception) {
                        info { "dinda ${e.message}" }
                    }
                }

                override fun onFailure(call: Call<BeritaDesaResponse>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })

    }

    fun getbanner(){
        //gambar desa
        var bannermodel = mutableListOf<BannerModel>()

        api.get_banner(Constant.API_KEY_BACKEND,sessionManager.getiduser().toString())
            .enqueue(object : Callback<BannerResponse> {
                override fun onResponse(
                    call: Call<BannerResponse>,
                    response: Response<BannerResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val data = response.body()
                            info { "dinda banner $data" }

                            if (response.body()!!.status == 0){
                                binding.shimmerbanner.visibility = View.GONE
                                binding.imgkosongbanner.visibility = View.VISIBLE
                                binding.sliderbanner.visibility = View.GONE
                            }
                            else if (response.body()!!.status == 1){
                                binding.shimmerbanner.visibility = View.GONE
                                binding.imgkosongbanner.visibility = View.GONE
                                binding.sliderbanner.visibility = View.VISIBLE

                                val imageList: ArrayList<String> = ArrayList()

                                for (banner in data!!.data!!){
                                    bannermodel.add(banner)
                                    //slider
                                    imageList.add(Constant.STORAGE + banner.foto!!)

                                }
                                setBannerSlider(imageList, binding.sliderbanner, bannermodel)


                            }


                        } else {
                            toast("gagal mendapatkan response")
                        }
                    } catch (e: Exception) {
                        info { "dinda ${e.message}" }
                    }
                }

                override fun onFailure(call: Call<BannerResponse>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

            })

    }
    private fun setImageInSlider(
        images: ArrayList<String>,
        imageSlider: SliderView,
        sliderModel: MutableList<BeritaDesaModel>
    ) {
        val adapter = BeritaDesaPopular()
        adapter.setDialog(object : BeritaDesaPopular.Dialog {
            override fun onClick(position: Int, note: BeritaDesaModel) {
                val gson = Gson()
                val noteJson = gson.toJson(note)
                startActivity<BeritaDesaDetailActivity>(
                    "beritadesa" to noteJson
                )

            }

        })
        adapter.renewItems(images, sliderModel)
        binding.imageSlider.setSliderAdapter(adapter)
        binding.imageSlider.isAutoCycle = true
        binding.imageSlider.scrollTimeInSec = 3
        binding.imageSlider.startAutoCycle()
    }

    private fun setBannerSlider(
        images: ArrayList<String>,
        imageSlider: SliderView,
        sliderModel: MutableList<BannerModel>
    ) {
        val adapter = BannerAdapter()
        adapter.setDialog(object : BannerAdapter.Dialog {
            override fun onClick(position: Int, note: BannerModel) {
                val gson = Gson()
                val noteJson = gson.toJson(note)
                startActivity<BannerActivity>(
                    "banner" to noteJson
                )

            }

        })
        adapter.renewItems(images, sliderModel)
        binding.sliderbanner.setSliderAdapter(adapter)
        binding.sliderbanner.isAutoCycle = true
        binding.sliderbanner.scrollTimeInSec = 3
        binding.sliderbanner.startAutoCycle()
    }




}