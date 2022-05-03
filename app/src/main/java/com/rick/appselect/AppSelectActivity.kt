package com.rick.appselect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rick.appselect.databinding.ActivityAppSelectBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppSelectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppSelectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAppSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}