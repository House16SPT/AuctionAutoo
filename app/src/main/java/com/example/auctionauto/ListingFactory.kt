package com.example.auctionauto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.auctionauto.data.BidRepo
import com.example.auctionauto.data.ListingRepo



class ListingVMFactory(private val repo: ListingRepo,private val bidRepo: BidRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListingViewModel(repo, bidRepo) as T
    }
}