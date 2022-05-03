package com.rick.appselect.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this@AppSelectActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.recyclerView.adapter = adapter

        viewModel.movieList.observe(this) { list ->
            viewModel.movieMutableList.addAll(list)
            // ответ API иногда отправляет один и тот же фильм
            viewModel.movieMutableList.toSet()
            adapter.moviesDiffer.submitList(viewModel.movieMutableList.toList())
        }


        viewModel.isLoading.observe(this) {
            if (it) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!viewModel.isLoading.value!!) {
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == adapter.moviesDiffer.currentList.size - 1) {
                        // проверьте, есть ли еще доступные данные из API
                        if (viewModel.hasMore.value == true) viewModel.loadMoreData()
                    }
                }
            }
        })

        viewModel.errorMessage.observe(this) {
            if (it.isNotBlank())
                Toast.makeText(this, getString(R.string.error_toast_message, it), Toast.LENGTH_LONG)
                    .show()
        }

        viewModel.isRefreshing.observe(this) {
            binding.root.isRefreshing = it
        }

        viewModel.hasMore.observe(this) {
            if (!it) Toast.makeText(this, getString(R.string.no_more_movies), Toast.LENGTH_SHORT)
                .show()
        }

        binding.root.setOnRefreshListener {
            viewModel.refreshData()
        }
    }

}