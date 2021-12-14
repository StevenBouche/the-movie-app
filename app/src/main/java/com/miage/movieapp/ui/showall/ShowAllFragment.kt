package com.miage.movieapp.ui.showall

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.miage.movieapp.R
import com.miage.movieapp.adapters.MoviesAdapter
import com.miage.movieapp.adapters.models.MovieView
import com.miage.movieapp.databinding.FragmentShowAllBinding
import com.miage.movieapp.handlers.IHandlerMovies
import com.miage.movieapp.models.MovieParcelable
import com.miage.movieapp.models.ShowAllType
import com.miage.movieapp.ui.fragment.BaseFragment
import com.miage.movieapp.ui.fragment.BaseViewModel
import com.miage.movieapp.utils.RecyclerScrollListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowAllFragment : BaseFragment<FragmentShowAllBinding>(), IHandlerMovies {

    private val moviesViewModel: ShowAllViewModel by viewModel()
    private lateinit var adapter: MoviesAdapter
    private val args by navArgs<ShowAllFragmentArgs>()

    override fun getViewModel(): BaseViewModel = moviesViewModel

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentShowAllBinding {
        return FragmentShowAllBinding.inflate(inflater, container, false)
    }

    override fun setupAdapters(){
        adapter = MoviesAdapter(this, RecyclerScrollListener(binding.moviesRecyclerView) { moviesViewModel.loadMoreMovies() })
    }

    override fun setupBinding(){
        binding.viewmodel = moviesViewModel
        binding.moviesRecyclerView.adapter = adapter
    }

    override fun setupViewModelObservers() {
        moviesViewModel.movieList.observe(viewLifecycleOwner) { adapter.submitList(it.map { element -> MovieView(element) }) }
    }

    override fun loadViewModel() {
        moviesViewModel.type = ShowAllType.valueOf(args.type.name)
    }

    override fun setupTitle() {
        val type = ShowAllType.valueOf(args.type.name)
        setTitle(getString(type.nameRes))
    }

    override fun onClick(item: MovieView) {
        val action = ShowAllFragmentDirections.actionShowAllFragmentToMovieDetailsFragment(MovieParcelable(item.id, item.title))
        findNavController().navigate(action)
    }

}