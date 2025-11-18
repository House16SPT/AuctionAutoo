package com.example.auctionauto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.auctionauto.data.ListingRepo


//This class was written by ChatGPT 5.1 Instant
class ListingVMFactory(private val repo: ListingRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListingViewModel(repo) as T
    }
}