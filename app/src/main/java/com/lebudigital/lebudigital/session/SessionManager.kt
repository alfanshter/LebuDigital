package com.lebudigital.lebudigital.session

import android.content.Context
import android.content.SharedPreferences

class SessionManager(private val context: Context?) {
    val privateMode = 0
    val privateName ="login"
    var Pref : SharedPreferences?=context?.getSharedPreferences(privateName,privateMode)
    var editor : SharedPreferences.Editor?=Pref?.edit()

    private val islogin = "login"
    fun setLogin(check: Boolean){
        editor?.putBoolean(islogin,check)
        editor?.commit()
    }

    fun getLogin():Boolean?
    {
        return Pref?.getBoolean(islogin,false)
    }

    private val isloginadmin = "loginadmin"
    fun setLoginadmin(check: Boolean){
        editor?.putBoolean(isloginadmin,check)
        editor?.commit()
    }

    fun getLoginadmin():Boolean?
    {
        return Pref?.getBoolean(isloginadmin,false)
    }


    private val iduser = "iduser"
    fun setiduser(check:Int)
    {
        editor?.putInt(iduser,check)
        editor?.commit()
    }

    fun getiduser():Int?
    {
        return Pref?.getInt(iduser,0)
    }

    private val province_id = "province_id"
    fun setprovince_id(check:String)
    {
        editor?.putString(province_id,check)
        editor?.commit()
    }

    fun getprovince_id():String?
    {
        return Pref?.getString(province_id,"")
    }

    private val regencie_id = "regencie_id"
    fun setregencie_id(check:String)
    {
        editor?.putString(regencie_id,check)
        editor?.commit()
    }

    fun getregencie_id():String?
    {
        return Pref?.getString(regencie_id,"")
    }

    private val district_id = "district_id"
    fun setdistrict_id(check:String)
    {
        editor?.putString(district_id,check)
        editor?.commit()
    }

    fun getdistrict_id():String?
    {
        return Pref?.getString(district_id,"")
    }

    private val village_id = "village_id"
    fun setvillage_id(check:String)
    {
        editor?.putString(village_id,check)
        editor?.commit()
    }

    fun getvillage_id():String?
    {
        return Pref?.getString(village_id,"")
    }

}
