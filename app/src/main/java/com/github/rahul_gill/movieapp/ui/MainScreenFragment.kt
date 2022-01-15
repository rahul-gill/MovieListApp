package com.github.rahul_gill.movieapp.ui

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.rahul_gill.movieapp.AppNavGraphDirections
import com.github.rahul_gill.movieapp.R
import com.github.rahul_gill.movieapp.databinding.MainScreenLayoutBinding
import com.github.rahul_gill.movieapp.models.Movie
import com.github.rahul_gill.movieapp.models.ResponseWrapper
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus


@AndroidEntryPoint
class MainScreenFragment: Fragment() {
    private lateinit var binding: MainScreenLayoutBinding
    private lateinit var movieListAdapter: MovieListAdapter
    private val viewModel: AppViewModel by activityViewModels()
    private val args: MainScreenFragmentArgs by navArgs()
    private var searchExpanded: Boolean = false
    private lateinit var onSearchTextChangeJob: Job


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = MainScreenLayoutBinding.inflate(layoutInflater)
        movieListAdapter = MovieListAdapter(onClick = { id ->
            findNavController().navigate(AppNavGraphDirections.toDetailScreen(id))
        })
        binding.movieList.adapter = movieListAdapter


        onSearchTextChangeJob = Job()
        binding.searchEditText.addTextChangedListener(
            {_,_,_,_ -> },
            {text,_,_,_ ->
                onSearchTextChangeJob.cancel()
                if(text?.isNotBlank() == true) {
                    onSearchTextChangeJob = Job()
                    (lifecycleScope + onSearchTextChangeJob).launch {
                        println("job started with text: $text")
                        delay(100)
                        viewModel.getSearchedMovieList(text.toString())
                        println("job ended with text: $text")
                    }
                }
            },
            { }
        )


        binding.topAppBar.setOnMenuItemClickListener{ item ->
            if(item.itemId == R.id.m_search) setupSearchBar(item)
            true
        }
        setupSearchBar(binding.topAppBar.menu.findItem(R.id.m_search), onStart = true)





        if(args.initiatedForSearch) viewModel.setMovieListToNoneType()
        else  lifecycleScope.launch { viewModel.getMovieList() }

        observeMoviesList()

        return binding.root
    }


    private fun observeMoviesList(){
        viewModel.movieList.observe(viewLifecycleOwner){
            when (it) {
                is ResponseWrapper.LoadingInProgress -> {
                    binding.shimmerLayout.startShimmer()
                }
                is ResponseWrapper.Error -> {
                    binding.shimmerLayout.stopShimmer()
                    binding.shimmerLayout.visibility = View.GONE
                    makeSnackBar(it.message)
                }
                is ResponseWrapper.Result -> {
                    binding.shimmerLayout.stopShimmer()
                    binding.shimmerLayout.visibility = View.GONE
                    if (it.data.isEmpty()){
                        binding.movieList.visibility = View.GONE
                        binding.zeroSizeListInfo.visibility = View.VISIBLE
                    }
                    else {
                        binding.movieList.visibility = View.VISIBLE
                        binding.zeroSizeListInfo.visibility = View.GONE
                        movieListAdapter.submitList(it.data.map { m -> Movie(m.id, m.name, m.imageUrl, m.summary) })
                    }
                }
                else -> {
                    binding.shimmerLayout.stopShimmer()
                    binding.shimmerLayout.visibility = View.GONE
                }
            }
        }
    }

    private fun setupSearchBar(item: MenuItem, onStart: Boolean = false){
        if(onStart && args.initiatedForSearch){
            println("block .")
            binding.movieList.visibility = View.GONE
            binding.searchEditText.visibility = View.VISIBLE
            binding.searchEditText.requestFocus()
            searchExpanded = true
            val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
            val b = imm?.showSoftInput(binding.searchEditText, 0)
            println(b)
            item.setIcon(R.drawable.ic_baseline_close_24)
        }else if(onStart && !args.initiatedForSearch){
            println("block ....")
            binding.movieList.visibility = View.VISIBLE
            binding.searchEditText.visibility = View.GONE
            searchExpanded = false
        }

        else if(searchExpanded){
            println("block A")
            binding.searchEditText.visibility = View.GONE
            binding.movieList.visibility = View.VISIBLE
            lifecycleScope.launch { viewModel.getMovieList() }
            item.setIcon(R.drawable.ic_baseline_search_24)
            searchExpanded = false
            val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
        }
        else{
            println("block B")
            binding.movieList.visibility = View.GONE
            binding.searchEditText.visibility = View.VISIBLE
            binding.searchEditText.requestFocus()
            searchExpanded = true
            val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
            val b = imm?.showSoftInput(binding.searchEditText, 0)
            println(b)
            item.setIcon(R.drawable.ic_baseline_close_24)
        }
    }

    private fun makeSnackBar(message: String){
        val x = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
        x.setBackgroundTint(resources.getColor(R.color.card_background, null))
        x.setTextColor(resources.getColor(R.color.white, null))
        x.setAction("Dismiss"){ x.dismiss() }
        x.show()
    }
}