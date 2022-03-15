package com.lebudigital.lebudigital.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lebudigital.lebudigital.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtsignup.setOnClickListener {
            startActivity<RegisterActivity>()
        }

        txtloginemail.setOnClickListener {
            startActivity<LoginEmailActivity>()
        }


    }
}