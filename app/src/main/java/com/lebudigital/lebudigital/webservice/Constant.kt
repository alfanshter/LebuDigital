package com.lebudigital.lebudigital.webservice

import android.app.ProgressDialog

object Constant {
    val API_KEY_BACKEND = "EicX7TikP86NQeZx6GLvtdqXBe7inf6GYVx8chsOBds95AndoZ3rmTrVDjATK3JT"
    val folder_foto ="http://kasir-andrea.pws-solution.com/public/storage/"
    val api_backend ="http://202.111.28.118:8000/api/"
    val api_rajaongkir ="ce29cd22bcbb3cea880844b4772fe517"
    val firebase_realtime = "https://lebudigital-c0249-default-rtdb.asia-southeast1.firebasedatabase.app/"
    const val STORAGE = "http://202.111.28.118:8000/storage/"
    fun loading(status: Boolean,progressDialog : ProgressDialog) {
        if (status) {
            progressDialog.setTitle("Loading...")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
        } else {
            progressDialog.dismiss()
        }
    }
}