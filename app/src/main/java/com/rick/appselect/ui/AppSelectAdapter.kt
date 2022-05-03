package com.rick.appselect.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.rick.appselect.R
import com.rick.appselect.domain.model.Result

class AppSelectAdapter(private val activity: AppSelectActivity) :
    RecyclerView.Adapter<AppSelectAdapter.AppSelectViewHolder>() {

    private val moviesDiffUtil = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.summary == newItem.summary
        }
    }

    val moviesDiffer = AsyncListDiffer(this, moviesDiffUtil)

    inner class AppSelectViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal val title = view.findViewById<MaterialTextView>(R.id.movieName)
        internal val summary = view.findViewById<MaterialTextView>(R.id.movieSummary)
        internal val image = view.findViewById<ShapeableImageView>(R.id.movieImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppSelectViewHolder {
        return AppSelectViewHolder(
            LayoutInflater.from(parent.context).inflate((R.layout.movie_entry), parent, false)
        )
    }

    override fun onBindViewHolder(holder: AppSelectViewHolder, position: Int) {
        val current = moviesDiffer.currentList[position]
        with(holder) {
            this.title.text = current.title
            this.summary.text = current.summary
            if (current.multimedia.src.isNotBlank()) {
                Glide.with(activity)
                    .load(current.multimedia.src)
                    .into(this.image)
            }
        }
    }

    override fun getItemCount(): Int = moviesDiffer.currentList.size
}