package com.lebudigital.lebudigital.auth

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.lebudigital.lebudigital.MainActivity
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.databinding.ActivityLoginBinding
import com.lebudigital.lebudigital.model.login.LoginResponse
import com.lebudigital.lebudigital.session.SessionManager
import com.lebudigital.lebudigital.session.SessionProfilManager
import com.lebudigital.lebudigital.webservice.ApiClient
import com.lebudigital.lebudigital.webservice.Constant
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), AnkoLogger {
    lateinit var binding: ActivityLoginBinding
    lateinit var progressDialog: ProgressDialog
    var api = ApiClient.instance()
    lateinit var sessionManager: SessionManager
    lateinit var sessionProfilManager: SessionProfilManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)
        sessionManager = SessionManager(this)
        sessionProfilManager = SessionProfilManager(this)
        txtsignup.setOnClickListener {
            startActivity<RegisterActivity>()
        }

//        txtloginemail.setOnClickListener {
//            startActivity<LoginEmailActivity>()
//        }

        binding.btnlogin.setOnClickListener {
            login(it)
        }


    }


    fun login(view: View) {
        val password = binding.edtpassword.text.toString().trim()
        val username = binding.edtusernmae.text.toString().trim()

        if (password.isNotEmpty() &&
            username.isNotEmpty()
        ) {
            Constant.loading(true, progressDialog)
            api.login(
                username, password
            ).enqueue(object :
                Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            if (response.body()!!.status == 1) {
                                toast("Login berhasil")
                                sessionManager.setLogin(true)
                                sessionManager.setiduser(response.body()!!.data!!.id!!)
                                info { "dinda ${response.body()!!.data}" }
                                sessionProfilManager.setNama(response.body()!!.data!!.name!!)

                                sessionManager.setprovince_id(response.body()!!.data!!.provinceId!!)
                                sessionManager.setregencie_id(response.body()!!.data!!.regencieId!!)
                                sessionManager.setdistrict_id(response.body()!!.data!!.districtId!!)
                                sessionManager.setvillage_id(response.body()!!.data!!.villageId!!)
                                Constant.loading(false, progressDialog)
                                startActivity<MainActivity>()
                            } else {
                                Constant.loading(false, progressDialog)
                                toast("Username atau password salah")

                            }
                        } else {
                            info { "dinda response ${response.code()}" }
                            Constant.loading(false, progressDialog)
                            toast("response salah")
                        }
                    } catch (e: Exception) {
                        info { "dinda cath ${e.message}" }

                    }

                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    info { "dinda failure ${t.message}" }
                    toast("silahkan hubungi developer")

                }

            })

        } else {
            Snackbar.make(view, "Jangan kosongi kolom", 3000).show()
        }


    }

}