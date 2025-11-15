package com.example.auctionauto

import android.content.ContentValues
import android.content.Context

class ListingRepository(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun addListing(
        userId: Int,
        carName: String,
        description: String,
        price: Double,
        tags: String,
        timeLimit: String
    ): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("user_id", userId)
            put("car_name", carName)
            put("description", description)
            put("price", price)
            put("tags", tags)
            put("time_limit", timeLimit)
        }
        val result = db.insert("listings", null, values)
        db.close()
        return result != -1L
    }

    fun getAllListings(): List<Map<String, Any>> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM listings", null)
        val list = mutableListOf<Map<String, Any>>()

        if (cursor.moveToFirst()) {
            do {
                list.add(
                    mapOf(
                        "id" to cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        "car_name" to cursor.getString(cursor.getColumnIndexOrThrow("car_name")),
                        "description" to cursor.getString(cursor.getColumnIndexOrThrow("description")),
                        "price" to cursor.getDouble(cursor.getColumnIndexOrThrow("price")),
                        "tags" to cursor.getString(cursor.getColumnIndexOrThrow("tags")),
                        "time_limit" to cursor.getString(cursor.getColumnIndexOrThrow("time_limit"))
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return list
    }

    fun searchListings(query: String): List<Map<String, Any>> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM listings WHERE car_name LIKE ? OR tags LIKE ?",
            arrayOf("%$query%", "%$query%")
        )
        val results = mutableListOf<Map<String, Any>>()

        if (cursor.moveToFirst()) {
            do {
                results.add(
                    mapOf(
                        "id" to cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        "car_name" to cursor.getString(cursor.getColumnIndexOrThrow("car_name")),
                        "price" to cursor.getDouble(cursor.getColumnIndexOrThrow("price"))
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return results
    }

    fun getUserListings(userId: Int): List<Map<String, Any>> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM listings WHERE user_id=?", arrayOf(userId.toString()))
        val list = mutableListOf<Map<String, Any>>()

        if (cursor.moveToFirst()) {
            do {
                list.add(
                    mapOf(
                        "id" to cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        "car_name" to cursor.getString(cursor.getColumnIndexOrThrow("car_name")),
                        "price" to cursor.getDouble(cursor.getColumnIndexOrThrow("price"))
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return list
    }
}
