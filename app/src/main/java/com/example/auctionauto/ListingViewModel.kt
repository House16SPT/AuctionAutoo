package com.example.auctionauto

import com.example.auctionauto.data.Listing
import androidx.lifecycle.ViewModel
import com.example.auctionauto.data.ListingRepo
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


// This class was written by ChatGPT 5.1 Instant
class ListingViewModel(private val repo: ListingRepo): ViewModel() {
    private val _listings = MutableStateFlow<List<Listing>>(emptyList())
    val listings: StateFlow<List<Listing>> = _listings

    fun loadListings(){
        viewModelScope.launch {
            _listings.value = repo.getAllListings()
        }
    }

    fun addListing(listing: Listing) {
        viewModelScope.launch {
            repo.insertListing(listing)
        }
    }

    fun increasePrice(id: Int) {
        viewModelScope.launch {
            repo.increasePrice(id)
            loadListings() // refresh list after update
        }
    }

}