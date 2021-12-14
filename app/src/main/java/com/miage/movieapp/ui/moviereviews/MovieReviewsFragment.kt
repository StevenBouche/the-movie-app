package com.miage.movieapp.ui.moviereviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.miage.movieapp.R
import com.miage.movieapp.databinding.FragmentMovieReviewsBinding
import com.miage.movieapp.adapters.MovieReviewsAdapter
import com.miage.movieapp.extension.setVisibilityListEmpty
import com.miage.movieapp.ui.fragment.BaseFragment
import com.miage.movieapp.ui.fragment.BaseViewModel
import com.miage.movieapp.utils.RecyclerScrollListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieReviewsFragment: BaseFragment<FragmentMovieReviewsBinding>() {

    private lateinit var adapter: MovieReviewsAdapter

    private val movieViewModel: MovieReviewsViewModel by viewModel()
    private val args by navArgs<MovieReviewsFragmentArgs>()

    override fun getViewModel(): BaseViewModel = movieViewModel

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMovieReviewsBinding {
        return FragmentMovieReviewsBinding.inflate(inflater, container, false)
    }

    override fun setupAdapters(){
        adapter = MovieReviewsAdapter(RecyclerScrollListener(binding.reviewsList) { movieViewModel.loadMoreReviews() })
    }

    override fun setupBinding(){
        binding.reviewsList.adapter = adapter
    }

    override fun setupViewModelObservers() {
        movieViewModel.reviews.observe(viewLifecycleOwner) {
            binding.NoContent.setVisibilityListEmpty(it)
            adapter.submitList(it)
        }
    }

    override fun loadViewModel() {
        movieViewModel.movieParcelable = args.movie
    }

    override fun setupTitle() {
        val label = getString(R.string.movie_review)
        val title = args.movie.title
        setTitle("$label : $title")
    }
}