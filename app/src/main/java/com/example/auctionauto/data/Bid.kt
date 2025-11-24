package com.example.auctionauto.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bids")
data class Bid(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val listingId: Int,
    val bidderEmail: String
)