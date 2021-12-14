package com.miage.movieapp.ui.moviedetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.core.models.api.AccountMovie
import com.example.core.models.api.Cast
import com.example.core.models.api.Movie
import com.example.core.models.api.MovieVideo
import com.miage.movieapp.R
import com.miage.movieapp.databinding.FragmentMovieDetailsBinding
import com.miage.movieapp.extension.*
import com.miage.movieapp.handlers.IHandlerMovieDetails
import com.miage.movieapp.models.MovieParcelable
import com.miage.movieapp.models.VideoParcelable
import com.miage.movieapp.adapters.CastsAdapter
import com.miage.movieapp.adapters.VideosAdapter
import com.miage.movieapp.ui.fragment.BaseFragment
import com.miage.movieapp.ui.fragment.BaseViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailsFragment: BaseFragment<FragmentMovieDetailsBinding>(), IHandlerMovieDetails {

    private lateinit var adapterVideos: VideosAdapter
    private lateinit var adapterCasts: CastsAdapter

    private val movieViewModel: MovieDetailsViewModel by viewModel()
    private val args by navArgs<MovieDetailsFragmentArgs>()

    private val disposables: CompositeDisposable = CompositeDisposable()
    private val actionFavorite: Completable = Completable.fromAction(this::actionFavorite).subscribeOn(Schedulers.io())

    override fun getViewModel(): BaseViewModel = movieViewModel

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentMovieDetailsBinding {
        return FragmentMovieDetailsBinding.inflate(inflater, container, false)
    }

    override fun setupAdapters(){
        adapterVideos = VideosAdapter(this)
        adapterCasts = CastsAdapter()
    }

    override fun setupBinding(){
        binding.viewmodel = movieViewModel
        binding.videosRecyclerView.adapter = adapterVideos
        binding.castRecyclerView.adapter = adapterCasts
        binding.reviewsButton.setOnClickListener { onClickReviews(movieViewModel.movieParcelable) }
        binding.imageFavorite.setOnClickListener {
            disposables.add(actionFavorite.subscribe())
        }
    }

    override fun setupViewModelObservers() {
        movieViewModel.movie.observe(viewLifecycleOwner) { bindMovieToView(it) }
        movieViewModel.videos.observe(viewLifecycleOwner) { bindMovieVideosToView(it) }
        movieViewModel.casts.observe(viewLifecycleOwner) { bindMovieCastsToView(it) }
        movieViewModel.account.observe(viewLifecycleOwner) {
            bindRated(it)
        }
        movieViewModel.movieFavorite.observe(viewLifecycleOwner) {
            val image = if(it != null) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
            binding.imageFavorite.setImageResource(image)
        }
    }

    private fun bindRated(it: AccountMovie?) {
        val rated = it?.accountRated()
        if(rated == null){
            binding.ratingButton.visibility =  View.VISIBLE
            binding.sliderRatingBar.visibility = View.VISIBLE
            binding.sliderRatingBar.addOnChangeListener { slider, value, fromUser -> binding.myRatingBar.setRatingBar(value, 5) }
            binding.ratingButton.setOnClickListener { movieViewModel.rateMovie(binding.sliderRatingBar.value) }
        } else {
            binding.ratingButton.visibility =  View.GONE
            binding.sliderRatingBar.visibility = View.GONE
            binding.myRatingBar.setRatingBar(rated.value, 5)
        }
    }

    private fun bindMovieVideosToView(it: List<MovieVideo>) {
        binding.watchTrailerText.setVisibilityList(it)
        adapterVideos.submitList(it)
    }

    private fun bindMovieCastsToView(it: List<Cast>) {
        binding.castText.setVisibilityList(it)
        adapterCasts.submitList(it)
    }

    override fun loadViewModel() {
        movieViewModel.movieParcelable = args.movie
    }

    override fun setupTitle() {
        setTitle(args.movie.title)
    }

    private fun actionFavorite(){
        movieViewModel.actionFavorite()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    private fun bindMovieToView(movie: Movie){
        binding.movieImage.setBackdropImageWithLoading(movie.backdropPath, binding.movieImageProgressBar)
        binding.ratingBar.setRatingBar(movie, 5)
        binding.genresText.setGenresText(movie.genres)
        binding.episodeText.setMovieRuntime(movie.releaseDate)
        binding.seasonText.setMovieRuntime(movie.runtime)
        binding.airDateText.setMovieLanguage(movie.originalLanguage)
        binding.titleText.text = movie.title
        val numVotes = "${movie.voteCount} votes"
        binding.numOfVotes.text = numVotes
        binding.overviewText.text = movie.overview
    }

    override fun onClickVideo(item: MovieVideo) {
        item.key?.let {
            val action = MovieDetailsFragmentDirections.actionMovieDetailsToMovieVideo(VideoParcelable(it))
            findNavController().navigate(action)
        }
    }

    private fun onClickReviews(item: MovieParcelable?){
        item?.let {
            val action = MovieDetailsFragmentDirections.actionMovieDetailsToMovieReviews(it)
            findNavController().navigate(action)
        }
    }
}