package com.miage.movieapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding4.widget.textChanges
import com.jakewharton.rxrelay3.PublishRelay
import com.miage.movieapp.adapters.MoviesAdapter
import com.miage.movieapp.adapters.models.MovieView
import com.miage.movieapp.databinding.FragmentSearchBinding
import com.miage.movieapp.extension.setVisibilityListEmpty
import com.miage.movieapp.handlers.IHandlerMovies
import com.miage.movieapp.models.MovieParcelable
import com.miage.movieapp.ui.fragment.BaseFragment
import com.miage.movieapp.utils.RecyclerScrollListener
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit
import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.miage.movieapp.ui.fragment.BaseViewModel


class SearchFragment: BaseFragment<FragmentSearchBinding>(), IHandlerMovies {

    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var adapter: MoviesAdapter

    private val disposables: CompositeDisposable = CompositeDisposable()
    private val onDestroyView: PublishRelay<Unit> = PublishRelay.create()

    override fun getViewModel(): BaseViewModel = searchViewModel

    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    override fun setupAdapters(){
        adapter = MoviesAdapter(this, RecyclerScrollListener(binding.moviesRecyclerView) { searchViewModel.loadMoreMovies() })
    }

    override fun setupBinding(){
        binding.moviesRecyclerView.adapter = adapter
    }

    override fun setupViewModelObservers() {
        searchViewModel.movieList.observe(viewLifecycleOwner) {
            if(searchViewModel.page!! >= 1){
                binding.NoContent.setVisibilityListEmpty(it)
            }
            adapter.submitList(it.map { element -> MovieView(element) })
            hideKeyboard(activity!!)
        }
    }

    override fun loadViewModel() {
        setupSearchBox()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    private fun setupSearchBox() {
        val disposable = binding.etSearchBox.textChanges()
            .debounce(500, TimeUnit.MILLISECONDS)
            .map { text -> text.toString() }
            .takeUntil(onDestroyView)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                searchViewModel.getMoviesSearch(it) {
                    adapter.clearList()
                }
            }
        disposables.add(disposable)
    }

    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }

    override fun onClick(item: MovieView) {
        val action = SearchFragmentDirections.actionSearcFragmentToMovieDetailsFragment(MovieParcelable(item.id, item.title))
        findNavController().navigate(action)
    }

}