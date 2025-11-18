package com.example.auctionauto.data

class ListingRepo(private val listingDao: ListingDao) {
    suspend fun insertListing(listing: Listing) =
        listingDao.insertListing(listing)

    suspend fun getAllListings() =
        listingDao.getAllListings()

    suspend fun getListing(id: Int) =
        listingDao.getListingById(id)

    suspend fun deleteListing(listing: Listing) =
        listingDao.deleteListing(listing)

    suspend fun increasePrice(id: Int) =
        listingDao.increasePrice(id)
}