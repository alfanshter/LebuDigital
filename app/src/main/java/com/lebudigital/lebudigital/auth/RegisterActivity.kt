package com.lebudigital.lebudigital.auth

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.lebudigital.lebudigital.R
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.startActivity


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        txtsudahpunyaakun.setOnClickListener {
            startActivity<LoginActivity>()
        }
        val text =
            "<font color=#8A8A8A>By signing up, you’re agree to our </font> <font color=#B70100>Terms & Conditions</font> <font color=#8A8A8A>and </font><font color=#B70100>and Privacy Policy</font>"
        txtketerangan.text = Html.fromHtml(text)
    }
}