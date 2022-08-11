package com.lebudigital.lebudigital.session

import android.content.Context
import android.content.SharedPreferences

class SessionProfilManager(private val context: Context?) {
    val privateMode = 0
    val privateName ="login"
    var Pref : SharedPreferences?=context?.getSharedPreferences(privateName,privateMode)
    var editor : SharedPreferences.Editor?=Pref?.edit()

    private val isnama = "nama"
    fun setNama(check: String){
        editor?.putString(isnama,check)
        editor?.commit()
    }

    fun getNama():String?
    {
        return Pref?.getString(isnama,"")
    }

    private val istelepon = "telepon"
    fun setTelepon(check: String){
        editor?.putString(istelepon,check)
        editor?.commit()
    }

    fun getTelepon():String?
    {
        return Pref?.getString(istelepon,"")
    }

    private val isnik = "nik"
    fun setNik(check: String){
        editor?.putString(isnik,check)
        editor?.commit()
    }

    fun getNik():String?
    {
        return Pref?.getString(isnik,"")
    }

    private val isalamat = "alamat"
    fun setAlamat(check: String){
        editor?.putString(isalamat,check)
        editor?.commit()
    }

    fun getAlamat():String?
    {
        return Pref?.getString(isalamat,"")
    }

    private val isemail = "email"
    fun setEmail(check: String){
        editor?.putString(isemail,check)
        editor?.commit()
    }

    fun getEmail():String?
    {
        return Pref?.getString(isemail,"")
    }




}
