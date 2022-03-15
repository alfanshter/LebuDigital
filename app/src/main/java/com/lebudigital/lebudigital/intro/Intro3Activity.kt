package com.lebudigital.lebudigital.intro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lebudigital.lebudigital.R
import com.lebudigital.lebudigital.auth.LoginActivity
import kotlinx.android.synthetic.main.activity_intro3.*

import org.jetbrains.anko.startActivity

class Intro3Activity : AppCompatActivity() {
    companion object{
        var activity = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro3)

        btnskip.setOnClickListener {
            startActivity<LoginActivity>()
            Intro1Activity.fa!!.finish()
            Intro2Activity.fa!!.finish()
            finish()
        }
        btnnext.setOnClickListener {
            startActivity<LoginActivity>()
            Intro1Activity.fa!!.finish()
            Intro2Activity.fa!!.finish()
            finish()
        }
    }
}