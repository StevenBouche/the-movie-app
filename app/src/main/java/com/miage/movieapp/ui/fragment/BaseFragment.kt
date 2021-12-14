package com.miage.movieapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.miage.movieapp.MainActivity

abstract class BaseFragment<T>: Fragment() where T : ViewDataBinding {

    protected lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = createBinding(inflater, container)
        binding.lifecycleOwner = viewLifecycleOwner
        setupAdapters()
        setupBinding()
        setupTitle()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        getViewModel()?.let {
            it.error.observe(viewLifecycleOwner) { error ->
                Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()
            }
        }
        setupViewModelObservers()
        loadViewModel()
    }

    abstract fun createBinding(inflater: LayoutInflater, container: ViewGroup?) : T

    protected fun setTitle(value: String?){
        (activity as MainActivity).supportActionBar?.title = value
    }

    open fun getViewModel(): BaseViewModel? { return null }
    open fun setupAdapters() { }
    open fun setupBinding() { }
    open fun setupTitle() { }
    open fun setupViewModelObservers() { }
    open fun loadViewModel(){ }

}