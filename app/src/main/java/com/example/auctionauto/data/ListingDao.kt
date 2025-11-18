package com.example.auctionauto.data

import androidx.room.*
@Dao
interface ListingDao {

    @Insert
    suspend fun insertListing(listing: Listing)

    @Query("SELECT * FROM listings")
    suspend fun getAllListings(): List<Listing>

    @Query("SELECT * FROM listings WHERE id = :id")
    suspend fun getListingById(id: Int): Listing?

    @Delete
    suspend fun deleteListing(listing: Listing)
}