package com.miage.movieapp.ui.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.core.models.api.Genre
import com.miage.movieapp.R
import com.miage.movieapp.databinding.FragmentMoviesBinding
import com.miage.movieapp.models.MovieParcelable
import com.miage.movieapp.adapters.MoviesAdapter
import com.miage.movieapp.adapters.models.MovieView
import com.miage.movieapp.handlers.IHandlerMovies
import com.miage.movieapp.ui.fragment.BaseFragment
import com.miage.movieapp.utils.RecyclerScrollListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.miage.movieapp.adapters.SortSpinnerAdapter
import com.miage.movieapp.handlers.IHandlerSpinnerSort
import com.miage.movieapp.models.DiscoverSortBy
import com.miage.movieapp.ui.fragment.BaseViewModel

class MoviesFragment: BaseFragment<FragmentMoviesBinding>(), IHandlerMovies, AdapterView.OnItemSelectedListener, IHandlerSpinnerSort {

    private val moviesViewModel: MoviesViewModel by viewModel()
    private lateinit var adapter: MoviesAdapter
    private val list: Array<DiscoverSortBy> = DiscoverSortBy.values()

    private val args by navArgs<MoviesFragmentArgs>()

    override fun getViewModel(): BaseViewModel = moviesViewModel

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMoviesBinding {
        return FragmentMoviesBinding.inflate(inflater, container, false)
    }

    override fun setupAdapters(){
        adapter = MoviesAdapter(this, RecyclerScrollListener(binding.moviesRecyclerView) { moviesViewModel.loadMoreMovies() })
    }

    override fun setupBinding(){
        binding.viewmodel = moviesViewModel
        binding.moviesRecyclerView.adapter = adapter
        setupSpinner()
    }

    override fun setupViewModelObservers() {
        moviesViewModel.movieList.observe(viewLifecycleOwner) { adapter.submitList(it.map { element -> MovieView(element) }) }
    }

    override fun loadViewModel() {
        moviesViewModel.setGenre(Genre(args.genre.id, args.genre.name)) {
            adapter.clearList()
        }
    }

    override fun setupTitle() {
        val label = getString(R.string.movie_genre)
        val genre = args.genre.name
        setTitle("$label : $genre")
    }

    private fun setupSpinner(){
        binding.spinner.onItemSelectedListener = this
        binding.spinner.adapter = SortSpinnerAdapter(this, list);
    }

    override fun onClick(item: MovieView) {
        val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(MovieParcelable(item.id, item.title));
        findNavController().navigate(action)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        moviesViewModel.setSortType(list[p2]) {
            adapter.clearList()
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun getLabel(value: Int): String {
        return getString(value)
    }

    override fun onClickItem(value: DiscoverSortBy) {
        println()
    }

}