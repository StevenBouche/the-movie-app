package com.miage.movieapp.ui.authentification

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding4.widget.textChanges
import com.jakewharton.rxrelay3.PublishRelay
import com.miage.movieapp.databinding.FragmentAuthBinding
import com.miage.movieapp.ui.fragment.BaseFragment
import com.miage.movieapp.ui.fragment.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class AuthFragment : BaseFragment<FragmentAuthBinding>() {

    private val onDestroyView: PublishRelay<Unit> = PublishRelay.create()
    private val authViewModel: AuthViewModel by viewModel()
    private val disposables: CompositeDisposable = CompositeDisposable()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAuthBinding {
        return FragmentAuthBinding.inflate(inflater, container, false)
    }

    override fun getViewModel(): BaseViewModel = authViewModel

    override fun setupBinding(){

        val disposable = binding.passwordEdit.textChanges()
            .debounce(500, TimeUnit.MILLISECONDS)
            .map { text -> text.toString() }
            .takeUntil(onDestroyView)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                authViewModel.password = it;
            }
        disposables.add(disposable)

        val disposable1 = binding.usernameEdit.textChanges()
            .debounce(500, TimeUnit.MILLISECONDS)
            .map { text -> text.toString() }
            .takeUntil(onDestroyView)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                authViewModel.username = it;
            }
        disposables.add(disposable1)

        binding.submit.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                authViewModel.tryAuth {
                    if(it){ goToHome() }
                    else {
                        Toast.makeText(activity, "Bad authentication", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun goToHome(){
        viewLifecycleOwner.lifecycleScope.launch {
            val action = AuthFragmentDirections.actionAuthFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}