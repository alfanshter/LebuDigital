package com.lebudigital.lebudigital.webservice

import android.app.ProgressDialog

object Constant {
//    val API_KEY_BACKEND = "EicX7TikP86NQeZx6GLvtdqXBe7inf6GYVx8chsOBds95AndoZ3rmTrVDjATK3JT"
//    val api_backend ="http://192.168.1.7:8000/api/"
//    const val STORAGE = "http://192.168.1.7:8000/storage/"

    val API_KEY_BACKEND = "B26zCb7TZqJujg03BasjIWAVplOrAtDObL68JYPmvmcJVi3zubZtODZUlQh4bPgP"
    val folder_foto ="http://kasir-andrea.pws-solution.com/public/storage/"
    val api_rajaongkir ="ce29cd22bcbb3cea880844b4772fe517"

//    val api_backend ="https://im.lebudigital.id/api/"
//    val firebase_realtime = "https://lebudigital-c0249-default-rtdb.asia-southeast1.firebasedatabase.app/"
//    const val STORAGE = "https://im.lebudigital.id/storage/"

    val api_backend ="http://192.168.1.15:8000/api/"
    val firebase_realtime = "https://lebudigital-c0249-default-rtdb.asia-southeast1.firebasedatabase.app/"
    const val STORAGE = "http://192.168.1.15:8000/storage/"


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