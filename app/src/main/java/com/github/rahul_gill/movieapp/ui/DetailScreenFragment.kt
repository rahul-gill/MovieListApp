package com.github.rahul_gill.movieapp.ui

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TableRow
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.github.rahul_gill.movieapp.R
import com.github.rahul_gill.movieapp.databinding.DetailScreenLayoutBinding
import com.github.rahul_gill.movieapp.models.MovieDetails
import com.github.rahul_gill.movieapp.models.ResponseWrapper
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailScreenFragment: Fragment() {
    private lateinit var binding: DetailScreenLayoutBinding
    private val viewModel: AppViewModel by activityViewModels()
    private val args: DetailScreenFragmentArgs by navArgs()
    private var item: MovieDetails? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailScreenLayoutBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        val id =args.id
        val list = (viewModel.movieList.value as ResponseWrapper.Result).data
        for(i in list) if(i.id == id) item = i

        binding.movie = item


        val options = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.movie_thumbnail_placeholder)
            .error(R.drawable.movie_thumbnail_placeholder)
        Glide.with(binding.root.context).load(item?.imageUrl ?: "")
            .apply(options)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.movieThumbnail)
        binding.movieThumbnail.setOnClickListener {
            if(item?.betterImageUrl == "-") makeSnackBar("Movie cover is not available")
            else showZoomableImage(item!!.betterImageUrl)
        }

        return binding.root
    }

    private fun makeSnackBar(message: String){
        val x = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
        x.setBackgroundTint(resources.getColor(R.color.card_background, null))
        x.setTextColor(resources.getColor(R.color.white, null))
        x.setAction("Dismiss"){ x.dismiss() }
        x.show()
    }

    private fun showZoomableImage(url: String ) {
        val d = Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        d.setCancelable(true)
        val wv = ImageView(requireContext())


        val r: Resources = requireContext().resources
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            64f,
            r.displayMetrics
        ).toInt()
        wv.adjustViewBounds = true

        val params = RelativeLayout.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(px,px,px,px)
        val rl = RelativeLayout(requireContext())
        rl.addView(wv, params)
        rl.gravity = Gravity.CENTER


        val options = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.movie_thumbnail_placeholder)
            .error(R.drawable.movie_thumbnail_placeholder)
        Glide.with(binding.root.context).load(url)
            .apply(options)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(wv)
        d.setContentView(rl)
        d.show()
    }

}
