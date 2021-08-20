package com.kazimasum.qrdemofirebase

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kazimasum.qrdemofirebase.R
import com.kazimasum.qrdemofirebase.MainActivity
import android.widget.TextView
import android.content.Intent
import android.view.View
import android.widget.Button
import com.kazimasum.qrdemofirebase.qrscanner

class MainActivity : AppCompatActivity() {
    var qrbtn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        qrbtn = findViewById<View>(R.id.qrbtn) as Button
        qrtext = findViewById<View>(R.id.qrtext) as TextView
        qrbtn!!.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext, qrscanner::class.java
                )
            )
        }
    }

    companion object {
        // @JvmField
        @SuppressLint("StaticFieldLeak")
        var qrtext: TextView? = null
    }
}