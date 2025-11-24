package com.example.auctionauto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auctionauto.data.Listing
import com.example.auctionauto.data.ListingRepo
import com.example.auctionauto.data.BidRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListingViewModel(
    private val listingRepo: ListingRepo,
    private val bidRepo: BidRepo
) : ViewModel() {

    private val _listings = MutableStateFlow<List<Listing>>(emptyList())
    val listings: StateFlow<List<Listing>> = _listings

    // Load all listings from DB
    fun loadListings() {
        viewModelScope.launch {
            _listings.value = listingRepo.getAllListings()
        }
    }

    fun deleteListing(listing: Listing) {
        viewModelScope.launch {
            listingRepo.deleteListing(listing)
            loadListings() // refresh UI
        }
    }
    // Add a bid for current user
    fun addBid(listingId: Int) {
        viewModelScope.launch {
            val email = UserSession.currentEmail ?: return@launch
            bidRepo.addBid(listingId, email)
        }
    }

    // Add new listing
    fun addListing(listing: Listing) {
        viewModelScope.launch {
            listingRepo.insertListing(listing)
            loadListings()
        }
    }

    // Increase price by 100 and refresh
    fun increasePrice(id: Int) {
        viewModelScope.launch {
            listingRepo.increasePrice(id)
            loadListings()
        }
    }
}
