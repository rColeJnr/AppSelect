package com.rick.appselect.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rick.appselect.R
import com.rick.appselect.databinding.ActivityAppSelectBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppSelectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppSelectBinding
    private lateinit var viewModel: AppSelectViewModel
    private lateinit var adapter: AppSelectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[AppSelectViewModel::class.java]

        adapter = AppSelectAdapter(this)

        binding.root.isRefreshing = false

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        viewModel.movieList.observe(this){ list ->
            adapter.moviesDiffer.submitList(list)
        }

//        viewModel.isLoading.observe(this){
//            //TODO remove loading icon, or something
//        }

        viewModel.errorMessage.observe(this){
            if (it.isNotBlank())
                Toast.makeText(this, getString(R.string.error_toast_message), Toast.LENGTH_LONG).show()
        }

//        viewModel.isRefresing.observe(this){
//            //TODO
//        }

        binding.root.setOnRefreshListener {
            refreshData()
        }
    }

    private fun refreshData() {
        TODO()
    }
}