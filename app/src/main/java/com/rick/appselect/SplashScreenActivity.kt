package com.rick.appselect

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        startActivity(Intent(this, AppSelectActivity::class.java))
        finish()

    }

    val stringa  = "r2hGllYW5TVGnqspbO8u3li1Un4AlQgQ"
}