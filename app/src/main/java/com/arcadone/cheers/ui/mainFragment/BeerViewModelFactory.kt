package com.arcadone.cheers.ui.mainFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arcadone.cheers.repository.BeerRepository
import kotlinx.coroutines.InternalCoroutinesApi

// By using viewModels and ViewModelProvider.Factory,
// the framework will take care of the lifecycle of the ViewModel.
// It will survive configuration changes and even if the Activity is recreated,
// you'll always get the right instance of the WordViewModel class.

class BeerViewModelFactory(private val repository: BeerRepository) : ViewModelProvider.Factory {
    @InternalCoroutinesApi
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}