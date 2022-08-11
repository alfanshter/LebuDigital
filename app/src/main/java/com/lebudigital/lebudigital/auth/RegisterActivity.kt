package com.lebudigital.lebudigital.auth

import android.app.ProgressDialog
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.databinding.ActivityRegisterBinding
import com.lebudigital.lebudigital.model.auth.PostRegister
import com.lebudigital.lebudigital.model.auth.PostResponse
import com.lebudigital.lebudigital.model.wilayah.ResponseKecamatan
import com.lebudigital.lebudigital.model.wilayah.ResponseKelurahan
import com.lebudigital.lebudigital.model.wilayah.ResponseKota
import com.lebudigital.lebudigital.model.wilayah.ResponseWilayah
import com.lebudigital.lebudigital.webservice.ApiClient
import com.lebudigital.lebudigital.webservice.Constant
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigInteger


class RegisterActivity : AppCompatActivity(), AnkoLogger {
    lateinit var binding: ActivityRegisterBinding
    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()

    //ID API WILAYAH
    var idprovinsi: Int? = null
    var idkota: Int? = null
    var idkecamatan: Int? = null
    var idkelurahan: BigInteger? = null
    var token: String? = null

    companion object {
        var nameprovinsi: String? = null
        var namekota: String? = null
        var namekecamatan: String? = null
        var namekelurahan: String? = null
        var no_telpon: String? = null
        var token: String? = null

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)

        txtsudahpunyaakun.setOnClickListener {
            startActivity<LoginActivity>()
        }
        val text =
            "<font color=#8A8A8A>By signing up, you’re agree to our </font> <font color=#B70100>Terms & Conditions</font> <font color=#8A8A8A>and </font><font color=#B70100>and Privacy Policy</font>"
        txtketerangan.text = Html.fromHtml(text)

        binding.btndaftar.setOnClickListener {
            daftar(it)
        }
        getprovinsi()
    }


    fun daftar(view: View) {
        val password = binding.edtpassword.text.toString()
        val telepon = binding.edttelepon.text.toString()
        val username = binding.edtemail.text.toString()
        val nama_lengkap = binding.edtnamalengkap.text.toString()
        val ulangi_password = binding.edtulangipassword.text.toString()
        val alamat_lengkap = binding.edtalamatlengkap.text.toString()

        if (password.isNotEmpty() &&
            telepon.isNotEmpty() &&
            username.isNotEmpty() &&
            nama_lengkap.isNotEmpty() &&
            ulangi_password.isNotEmpty() &&
            alamat_lengkap.isNotEmpty()
        ) {
            if (password == ulangi_password) {
                info { "dinda ${password} $idkelurahan $idprovinsi $nama_lengkap $idkecamatan $idkota $alamat_lengkap $username $telepon" }
                Constant.loading(true, progressDialog)
                api.register(
                    username,nama_lengkap,password,
                    idprovinsi!!.toString(), idkota!!.toString(), idkecamatan!!.toString(),
                    idkelurahan.toString(),alamat_lengkap,telepon
                ).enqueue(object :
                    Callback<PostResponse> {
                    override fun onResponse(
                        call: Call<PostResponse>,
                        response: Response<PostResponse>
                    ) {
                        try {
                            if (response.isSuccessful) {
                                if (response.body()!!.status == 1) {
                                    toast("pendaftaran berhasil")
                                    Constant.loading(false, progressDialog)
                                    finish()
                                } else if (response.body()!!.status == 2) {
                                    Constant.loading(false, progressDialog)
                                    toast("jangan kosongi kolom")
                                } else {
                                    Constant.loading(false, progressDialog)
                                    toast("silahkan ulangi lagi")

                                }
                            }else{
                                info { "dinda response ${response.code()}" }
                                Constant.loading(false, progressDialog)
                                toast("response salah")
                            }
                        } catch (e: Exception) {
                            info { "dinda cath ${e.message}" }

                        }

                    }

                    override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                        info { "dinda failure ${t.message}" }
                        toast("silahkan hubungi developer")

                    }

                })

            } else {
                Snackbar.make(view, "Password tidak sama", 3000).show()
            }
        } else {
            Snackbar.make(view, "Jangan kosongi kolom", 3000).show()
        }


    }

    fun getprovinsi() {
        api.getprovinsi()
            .enqueue(object : Callback<ResponseWilayah> {
                override fun onFailure(call: Call<ResponseWilayah>, t: Throwable) {
                    info { "dinda ${t.message}" }
                }

                override fun onResponse(
                    call: Call<ResponseWilayah>,
                    response: Response<ResponseWilayah>
                ) {
                    if (response.isSuccessful) {
                        val provinsi = ResponseWilayah.Provinsi()
                        provinsi.name = "Provinsi"
                        provinsi.id = -1
                        var spResponse: MutableList<ResponseWilayah.Provinsi> =
                            response.body()!!.data as MutableList<ResponseWilayah.Provinsi>
                        spResponse.add(0, provinsi)
                        val adapter: ArrayAdapter<ResponseWilayah.Provinsi> =
                            ArrayAdapter<ResponseWilayah.Provinsi>(
                                this@RegisterActivity,
                                R.layout.spinner_item,
                                spResponse
                            )
                        binding.spnprovinsi.adapter = adapter
                        binding.spnprovinsi.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                }

                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    if (position == 0) {

                                    }

                                    if (position > 0) {
                                        idprovinsi = spResponse[position].id
                                        nameprovinsi = spResponse[position].name
                                        info { "dinda prov $idprovinsi" }

                                        getkota()
                                    }
                                }

                            }

                    }
                }

            })
    }

    fun getkota() {
        if (idprovinsi != null) {
            api.getkota(idprovinsi!!).enqueue(object : Callback<ResponseKota> {
                override fun onFailure(call: Call<ResponseKota>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(
                    call: Call<ResponseKota>,
                    response: Response<ResponseKota>
                ) {
                    if (response.isSuccessful) {
                        val kota = ResponseKota.Kota()
                        kota.name = "Kota"
                        kota.id = -1

                        var kotaResponse: MutableList<ResponseKota.Kota> =
                            response.body()!!.data as MutableList<ResponseKota.Kota>
                        kotaResponse.add(0, kota)
                        val adapter: ArrayAdapter<ResponseKota.Kota> =
                            ArrayAdapter<ResponseKota.Kota>(
                                this@RegisterActivity,
                                R.layout.spinner_item,
                                kotaResponse
                            )
                        binding.spnkota.adapter = adapter
                        binding.spnkota.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                    TODO("Not yet implemented")
                                }

                                override fun onItemSelected(
                                    parent: AdapterView<*>?,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    if (position == 0) {

                                    }
                                    if (position > 0) {
                                        idkota = kotaResponse[position].id
                                        info { "dinda kota $idkota" }

                                        namekota = kotaResponse[position].name
                                        getkecamatan()
                                    }
                                }
                            }

                    }
                }

            })
        }
    }

    fun getkecamatan() {
        api.getkecamatan(idkota!!).enqueue(object : Callback<ResponseKecamatan> {
            override fun onFailure(call: Call<ResponseKecamatan>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<ResponseKecamatan>,
                response: Response<ResponseKecamatan>
            ) {
                if (response.isSuccessful) {
                    val kecamatan = ResponseKecamatan.Kecamatan()
                    kecamatan.name = "Kecamatan"
                    kecamatan.id = -1
                    var kecamatanresponse: MutableList<ResponseKecamatan.Kecamatan> =
                        response.body()!!.data as MutableList<ResponseKecamatan.Kecamatan>
                    kecamatanresponse.add(0, kecamatan)
                    val adapter: ArrayAdapter<ResponseKecamatan.Kecamatan> =
                        ArrayAdapter<ResponseKecamatan.Kecamatan>(
                            this@RegisterActivity,
                            R.layout.spinner_item,
                            kecamatanresponse
                        )
                    binding.spnkecamatan.adapter = adapter
                    binding.spnkecamatan.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onNothingSelected(parent: AdapterView<*>?) {

                            }

                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                if (position == 0) {

                                }
                                if (position > 0) {
                                    idkecamatan = kecamatanresponse[position].id
                                    info { "dinda kec $idkecamatan" }
                                    namekecamatan = kecamatanresponse[position].name
                                    getkelurahan()
                                }
                            }
                        }

                }
            }

        })
    }

    fun getkelurahan() {
        api.getkelurahan(idkecamatan!!).enqueue(object : Callback<ResponseKelurahan> {
            override fun onFailure(call: Call<ResponseKelurahan>, t: Throwable) {
                info { "dinda ayu ${t.message}" }
            }

            override fun onResponse(
                call: Call<ResponseKelurahan>,
                response: Response<ResponseKelurahan>
            ) {
                if (response.isSuccessful) {
                    val kelurahan = ResponseKelurahan.Kelurahan()
                    val bigInteger = (-1).toBigInteger()
                    kelurahan.name = "Kelurahan"
                    kelurahan.id =bigInteger
                    var kelurahanResponse: MutableList<ResponseKelurahan.Kelurahan> =
                        response.body()!!.data as MutableList<ResponseKelurahan.Kelurahan>
                    kelurahanResponse.add(0, kelurahan)
                    val adapter: ArrayAdapter<ResponseKelurahan.Kelurahan> =
                        ArrayAdapter<ResponseKelurahan.Kelurahan>(
                            this@RegisterActivity,
                            R.layout.spinner_item,
                            kelurahanResponse
                        )
                    binding.spndesa.adapter = adapter
                    binding.spndesa.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onNothingSelected(parent: AdapterView<*>?) {

                            }

                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                if (position == 0) {

                                }
                                if (position > 0) {
                                    idkelurahan = kelurahanResponse[position].id!!
                                    info { "dinda des $idkelurahan" }
                                    namekelurahan = kelurahanResponse[position].name

                                }
                            }

                        }
                }
            }

        })
    }

}