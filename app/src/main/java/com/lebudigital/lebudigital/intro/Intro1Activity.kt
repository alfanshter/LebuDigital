package com.lebudigital.lebudigital.intro

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.auth.LoginActivity
import kotlinx.android.synthetic.main.activity_intro1.*
import org.jetbrains.anko.startActivity


class Intro1Activity : AppCompatActivity() {
    companion object{
        var fa: Activity? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro1)
        fa = this
        btnskip.setOnClickListener {
            startActivity<LoginActivity>()
            finish()
        }
        btnnext.setOnClickListener {
            startActivity<Intro2Activity>()
        }
    }
}