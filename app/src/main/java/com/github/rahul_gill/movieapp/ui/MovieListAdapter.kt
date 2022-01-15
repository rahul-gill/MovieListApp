package com.github.rahul_gill.movieapp.ui

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.github.rahul_gill.movieapp.R
import com.github.rahul_gill.movieapp.databinding.MovieListItemBinding
import com.github.rahul_gill.movieapp.models.Movie


class MovieListAdapter(val onClick: (id: Int) -> Unit): ListAdapter<Movie, MovieListAdapter.ViewHolder>(MoviesDiffCallback())  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, onClick)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder private constructor(private val binding: MovieListItemBinding, val onClick: (id: Int) -> Unit): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Movie) {
            binding.movieTitle.text = item.name
            binding.movieSummary.text =
                if(item.summary == "") "No description provided"
                else Html.fromHtml(item.summary, Html.FROM_HTML_MODE_COMPACT)

            val options = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.movie_thumbnail_placeholder)
                .error(R.drawable.movie_thumbnail_placeholder)
            Glide.with(binding.root.context).load(item.imageUrl)
                .apply(options)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.movieThumbnail)
            binding.root.setOnClickListener{
                onClick(item.id)
            }
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup, onClick: (id: Int) -> Unit): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieListItemBinding.inflate(layoutInflater,parent,false )
                return ViewHolder(binding, onClick)
            }
        }
    }
}

class MoviesDiffCallback: DiffUtil.ItemCallback<Movie>(){
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}