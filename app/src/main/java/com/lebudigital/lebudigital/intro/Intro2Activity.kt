package com.lebudigital.lebudigital.intro

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.auth.LoginActivity
import kotlinx.android.synthetic.main.activity_intro2.*
import org.jetbrains.anko.startActivity

class Intro2Activity : AppCompatActivity() {
    companion object{
        var fa: Activity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro2)
        fa = this
        btnskip.setOnClickListener {
            startActivity<LoginActivity>()
            Intro1Activity.fa!!.finish()
            finish()
        }
        btnnext.setOnClickListener {
            startActivity<Intro3Activity>()
        }
    }
}