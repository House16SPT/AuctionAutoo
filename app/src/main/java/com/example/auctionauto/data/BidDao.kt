package com.example.auctionauto.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BidDao {

    @Insert
    suspend fun insertBid(bid: Bid)

    @Query("SELECT * FROM bids WHERE bidderEmail = :email")
    suspend fun getBidsByUser(email: String): List<Bid>
}