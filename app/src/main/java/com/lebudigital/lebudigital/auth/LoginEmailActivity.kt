package com.lebudigital.lebudigital.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lebudigital.lebudigital.R
import kotlinx.android.synthetic.main.activity_login_email.*
import org.jetbrains.anko.startActivity

class LoginEmailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_email)

        txtsignup.setOnClickListener {
            startActivity<RegisterActivity>()
        }

        txtlogintelepon.setOnClickListener {
            startActivity<LoginActivity>()
        }
    }
}