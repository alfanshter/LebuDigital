package com.lebudigital.lebudigital.ui.profil

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.databinding.ActivityUpdateProfilBinding
import com.lebudigital.lebudigital.model.auth.UpdataProfilResponse
import com.lebudigital.lebudigital.model.users.ProfilResponse
import com.lebudigital.lebudigital.model.users.UsersResponse
import com.lebudigital.lebudigital.webservice.ApiClient
import com.lebudigital.lebudigital.webservice.Constant
import com.squareup.picasso.Picasso
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File

class UpdateProfilActivity : AppCompatActivity(), AnkoLogger {
    lateinit var binding: ActivityUpdateProfilBinding
    var profil: ProfilResponse? = null

    lateinit var progressDialog: ProgressDialog

    //foto
    var filePath: Uri? = null
    var data: ByteArray? = null
    var data_akta: ByteArray? = null
    var data_kk: ByteArray? = null
    var data_ktp: ByteArray? = null
    private val REQUEST_PICK_IMAGE = 1
    private val REQUEST_PICK_IMAGE_KTP = 2
    private val REQUEST_PICK_IMAGE_KK = 3
    private val REQUEST_PICK_IMAGE_AKTA = 4

    var foto_profil: MultipartBody.Part? = null
    var foto_ktp: MultipartBody.Part? = null
    var foto_kk: MultipartBody.Part? = null
    var foto_akta: MultipartBody.Part? = null
    var api = ApiClient.instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_profil)
        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(this)


        val gson = Gson()
        profil = gson.fromJson(intent.getStringExtra("profil"), ProfilResponse::class.java)
        info { "dinda profil $profil" }
        binding.edtnama.setText(profil!!.profil!!.name)
        binding.edttelepon.setText(profil!!.profil!!.telepon)
        binding.edtemail.setText(profil!!.profil!!.email)
        binding.edtalamat.setText(profil!!.profil!!.alamatLengkap)
        if (profil!!.profil!!.nik == null) {
            binding.edtnik.hint = "Masukkan NIK"
        } else {
            binding.edtnik.setText(profil!!.profil!!.nik)
        }

        if (profil!!.profil!!.fotoKtp != null) {
            Picasso.get().load(Constant.STORAGE + profil!!.profil!!.fotoKtp).fit().centerCrop()
                .into(binding.imgfotoktp)
        }
        if (profil!!.profil!!.fotoKk != null) {
            Picasso.get().load(Constant.STORAGE + profil!!.profil!!.fotoKk).fit().centerCrop()
                .into(binding.imgkk)
        }
        if (profil!!.profil!!.foto_akta != null) {
            Picasso.get().load(Constant.STORAGE + profil!!.profil!!.foto_akta).fit().centerCrop()
                .into(binding.imgakta)
        }
        if (profil!!.profil!!.foto != null) {
            Picasso.get().load(Constant.STORAGE + profil!!.profil!!.foto).fit().centerCrop()
                .into(binding.imgfoto)
        }

        binding.imgfoto.setOnClickListener {
            pilihfile()
        }

        binding.imgakta.setOnClickListener {
            pilihfile_akta()
        }
        binding.imgfotoktp.setOnClickListener {
            pilihfile_ktp()
        }
        binding.imgkk.setOnClickListener {
            pilihfile_kk()
        }

        binding.btnupdate.setOnClickListener {
            update_profil(it)
        }
        binding.btnbatal.setOnClickListener {
            finish()
        }




    }

    private fun pilihfile() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_PICK_IMAGE)
    }

    private fun pilihfile_akta() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_PICK_IMAGE_AKTA)
    }

    private fun pilihfile_ktp() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_PICK_IMAGE_KTP)
    }

    private fun pilihfile_kk() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_PICK_IMAGE_KK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_PICK_IMAGE) {
                filePath = data?.data
                Picasso.get().load(filePath).fit().centerCrop().into(binding.imgfoto)
                convert()
            }

            if (requestCode == REQUEST_PICK_IMAGE_AKTA) {
                filePath = data?.data
                Picasso.get().load(filePath).fit().centerCrop().into(binding.imgakta)
                convert_akta()
            }

            if (requestCode == REQUEST_PICK_IMAGE_KTP) {
                filePath = data?.data
                Picasso.get().load(filePath).fit().centerCrop().into(binding.imgfotoktp)
                convert_ktp()
            }
            if (requestCode == REQUEST_PICK_IMAGE_KK) {
                filePath = data?.data
                Picasso.get().load(filePath).fit().centerCrop().into(binding.imgkk)
                convert_kk()
            }
        }
    }

    fun convert() {
        val bmp = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos)
        data = baos.toByteArray()

    }

    fun convert_akta() {
        val bmp = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos)
        data_akta = baos.toByteArray()

    }

    fun convert_ktp() {
        val bmp = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos)
        data_ktp = baos.toByteArray()

    }

    fun convert_kk() {
        val bmp = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos)
        data_kk = baos.toByteArray()

    }

    private fun update_profil(view: View) {

        val edtnama = binding.edtnama.text.toString().trim()
        val edtemail = binding.edtemail.text.toString().trim()
        val edttelepon = binding.edttelepon.text.toString().trim()
        val edtnik = binding.edtnik.text.toString().trim()
        val edtalamat = binding.edtalamat.text.toString().trim()

        if (edtnama.isNotEmpty() && edtemail.isNotEmpty() && edttelepon.isNotEmpty()
            && edtnik.isNotEmpty() && edtalamat.isNotEmpty()
        ) {
            loading(true)
            //foto
            val f: File = File(cacheDir, "foto")
            f.createNewFile()

            //foto_akta
            val f_akta: File = File(cacheDir, "foto_akta")
            f_akta.createNewFile()

            //foto_kk
            val f_kk: File = File(cacheDir, "foto_kk")
            f_kk.createNewFile()

            //foto_ktp
            val f_ktp: File = File(cacheDir, "foto_ktp")
            f_ktp.createNewFile()

            //Convert bitmap
            //ini bitmapnya

            //foto
            if (data != null) {
                val reqFile = RequestBody.create("image/*".toMediaTypeOrNull(), data!!)
                foto_profil = MultipartBody.Part.createFormData("foto", f.name, reqFile)
            }
            if (data_akta != null) {
                val reqFile = RequestBody.create("image/*".toMediaTypeOrNull(), data_akta!!)
                foto_akta = MultipartBody.Part.createFormData("foto_akta", f_akta.name, reqFile)
            }

            if (data_ktp != null) {
                val reqFile = RequestBody.create("image/*".toMediaTypeOrNull(), data_ktp!!)
                foto_ktp = MultipartBody.Part.createFormData("foto_ktp", f_ktp.name, reqFile)
            }

            if (data_kk != null) {
                val reqFile = RequestBody.create("image/*".toMediaTypeOrNull(), data_kk!!)
                foto_kk = MultipartBody.Part.createFormData("foto_kk", f_kk.name, reqFile)
            }


            val id: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                profil!!.profil!!.id.toString()
            )

            val name: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                edtnama
            )

            val email: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                edtemail
            )

            val telepon: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                edttelepon
            )

            val nik: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                edtnik
            )

            val alamat_lengkap: RequestBody = RequestBody.create(
                "text/plain".toMediaTypeOrNull(),
                edtalamat
            )

            api.update_profil_foto_kk_akta_ktp(
                Constant.API_KEY_BACKEND,
                id,
                name,
                email,
                telepon,
                nik,
                alamat_lengkap,
                foto_profil,
                foto_kk,
                foto_akta,
                foto_ktp
            ).enqueue(object :
                Callback<UpdataProfilResponse> {
                override fun onResponse(
                    call: Call<UpdataProfilResponse>,
                    response: Response<UpdataProfilResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            if (response.body()!!.status == 1) {
                                loading(false)
                                toast("update berhasil")
                                finish()
                            } else if (response.body()!!.status == 2) {
                                loading(false)
                                toast("jangan kosongi kolom")
                            } else {
                                loading(false)
                                toast("silahkan ulangi lagi")

                            }
                        }
                    } catch (e: Exception) {
                        loading(false)
                        info { "dinda cath ${e.message}" }

                    }

                }

                override fun onFailure(call: Call<UpdataProfilResponse>, t: Throwable) {
                    loading(false)
                    info { "dinda failure ${t.message}" }
                    toast("silahkan hubungi developer")

                }

            })

        } else {
            Snackbar.make(view, "Jangan kosongi kolom", 3000).show()
        }

    }

    fun loading(status: Boolean) {
        if (status) {
            progressDialog.setTitle("Loading...")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
        } else {
            progressDialog.dismiss()
        }
    }


}