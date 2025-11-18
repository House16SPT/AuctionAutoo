package com.example.auctionauto.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "listings")
data class Listing(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val make: String,
    val model: String,
    val year: String,
    val color: String,
    val price: Int,
    val duration: Int,
    val description: String,
    val author: String
)