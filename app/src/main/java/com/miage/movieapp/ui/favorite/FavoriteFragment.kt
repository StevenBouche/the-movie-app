package com.miage.movieapp.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.miage.movieapp.MainActivity
import com.miage.movieapp.adapters.MoviesAdapter
import com.miage.movieapp.adapters.models.MovieView
import com.miage.movieapp.databinding.FragmentFavoriteBinding
import com.miage.movieapp.handlers.IHandlerMovies
import com.miage.movieapp.models.MovieParcelable
import com.miage.movieapp.ui.fragment.BaseFragment
import com.miage.movieapp.ui.fragment.BaseViewModel
import com.miage.movieapp.utils.RecyclerScrollListener
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(), IHandlerMovies {

    private val viewModel: FavoriteViewModel by viewModel()
    private lateinit var adapter: MoviesAdapter

    override fun getViewModel(): BaseViewModel = viewModel;

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFavoriteBinding {
        return FragmentFavoriteBinding.inflate(inflater, container, false)
    }

    override fun setupAdapters(){
        adapter = MoviesAdapter(
            this,
            RecyclerScrollListener(binding.moviesRecyclerView) {  }
        )
    }

    override fun setupBinding(){
        binding.moviesRecyclerView.adapter = adapter
    }

    override fun setupViewModelObservers() {
        viewModel.movieList.observe(viewLifecycleOwner) {
            adapter.submitList(it.map { element -> MovieView(element) })
        }
    }

    override fun loadViewModel() {
        viewModel.getMovieFavorites()
    }

    override fun onClick(item: MovieView) {
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToMovieDetailsFragment(MovieParcelable(item.id, item.title))
        findNavController().navigate(action)
    }





}