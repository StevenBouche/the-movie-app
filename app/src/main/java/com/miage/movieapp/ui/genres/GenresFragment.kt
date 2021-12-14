package com.miage.movieapp.ui.genres

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.core.models.api.Genre
import com.miage.movieapp.databinding.FragmentGenresBinding
import com.miage.movieapp.handlers.IHandlerGenres
import com.miage.movieapp.models.GenreParcelable
import com.miage.movieapp.adapters.GenresAdapter
import com.miage.movieapp.ui.fragment.BaseFragment
import com.miage.movieapp.ui.fragment.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenresFragment : BaseFragment<FragmentGenresBinding>(), IHandlerGenres {

    private val genresViewModel: GenresViewModel by viewModel()
    private lateinit var adapter: GenresAdapter

    override fun getViewModel(): BaseViewModel = genresViewModel


    override fun createBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentGenresBinding {
        return FragmentGenresBinding.inflate(inflater, container, false)
    }

    override fun setupAdapters(){
        adapter = GenresAdapter(listOf(), this)
    }

    override fun setupBinding(){
        binding.categoryList.adapter = GenresAdapter(listOf(), this)
    }

    override fun setupViewModelObservers() {
        genresViewModel.categories.observe(viewLifecycleOwner) {
            binding.categoryList.adapter = GenresAdapter(it, this)
        }
    }

    override fun loadViewModel() {
        genresViewModel.getCategories()
    }

    override fun onClick(genre: Genre) {
        val action = GenresFragmentDirections.actionGenresFragmentToMoviesFragment(GenreParcelable(genre.id, genre.name));
        findNavController().navigate(action)
    }
}