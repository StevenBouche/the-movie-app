package com.miage.movieapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.core.models.api.Movie
import com.miage.movieapp.databinding.FragmentHomeBinding
import com.miage.movieapp.extension.setBackdropMovieImageWithLoading
import com.miage.movieapp.extension.setRatingBar
import com.miage.movieapp.handlers.IHandlerHome
import com.miage.movieapp.models.MovieParcelable
import com.miage.movieapp.adapters.HomeMoviesAdapter
import com.miage.movieapp.models.ShowAllParcelable
import com.miage.movieapp.models.ShowAllType
import com.miage.movieapp.ui.fragment.BaseFragment
import com.miage.movieapp.ui.fragment.BaseViewModel
import com.miage.movieapp.utils.RecyclerScrollListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(), IHandlerHome {

    private val moviesViewModel: HomeViewModel by viewModel()

    private lateinit var adapterPopular: HomeMoviesAdapter
    private lateinit var adapterUpcoming: HomeMoviesAdapter
    private lateinit var adapterNowPlaying: HomeMoviesAdapter
    private lateinit var adapterTopRated: HomeMoviesAdapter

    override fun getViewModel(): BaseViewModel = moviesViewModel

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun setupAdapters(){
        adapterPopular = HomeMoviesAdapter(this, RecyclerScrollListener(binding.popularRecyclerView) { moviesViewModel.loadMorePopular() })
        adapterUpcoming = HomeMoviesAdapter(this,RecyclerScrollListener(binding.upcomingRecyclerView) { moviesViewModel.loadMoreUpcoming() })
        adapterNowPlaying = HomeMoviesAdapter(this,RecyclerScrollListener(binding.inTheatersRecyclerView) { moviesViewModel.loadMoreNowPlaying() })
        adapterTopRated = HomeMoviesAdapter(this,RecyclerScrollListener(binding.topRecyclerView) { moviesViewModel.loadMoreTopRated() })
    }

    override fun setupBinding(){
        binding.viewmodel = moviesViewModel
        binding.popularRecyclerView.adapter = adapterPopular
        binding.upcomingRecyclerView.adapter = adapterUpcoming
        binding.inTheatersRecyclerView.adapter = adapterNowPlaying
        binding.topRecyclerView.adapter = adapterTopRated
        binding.showAllPopularText.setOnClickListener { showAllFragment(ShowAllType.POPULAR) }
        binding.showAllInTheatersText.setOnClickListener { showAllFragment(ShowAllType.THEATER) }
        binding.showAllUpcomingText.setOnClickListener { showAllFragment(ShowAllType.UPCOMING) }
        binding.showAllTopText.setOnClickListener { showAllFragment(ShowAllType.TOP_RATED) }
    }

    override fun setupViewModelObservers() {
        moviesViewModel.popularMovieList.observe(viewLifecycleOwner) { adapterPopular.submitList(it) }
        moviesViewModel.upcomingMovieList.observe(viewLifecycleOwner) { adapterUpcoming.submitList(it) }
        moviesViewModel.inTheatersMovieList.observe(viewLifecycleOwner) { adapterNowPlaying.submitList(it) }
        moviesViewModel.topMovieList.observe(viewLifecycleOwner) { adapterTopRated.submitList(it) }
        moviesViewModel.highlightedMovie.observe(viewLifecycleOwner) {
            binding.hlMovieImage.setBackdropMovieImageWithLoading(it, binding.highlighedMovieProgressBar)
            binding.hlRatingBar.setRatingBar(it.voteAverage, 5)
            binding.hlMovieImageLayout.setOnClickListener { _ -> this.onClickMovie(it) }
        }
    }

    override fun loadViewModel() {
        moviesViewModel.loadMorePopular()
        moviesViewModel.loadMoreNowPlaying()
        moviesViewModel.loadMoreUpcoming()
        moviesViewModel.loadMoreTopRated()
    }

    private fun showAllFragment(type: ShowAllType){
        val action = HomeFragmentDirections.actionHomeFragmentToShowAllFragment(ShowAllParcelable(type.name))
        findNavController().navigate(action)
    }

    override fun onClickMovie(item: Movie) {
        val action = HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(MovieParcelable(item.id, item.title))
        findNavController().navigate(action)
    }
}