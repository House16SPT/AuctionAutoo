package com.example.auctionauto

import android.content.ContentValues
import android.content.Context

class PaymentRepository(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun recordPayment(
        userId: Int,
        listingId: Int,
        amount: Double,
        status: String = "Completed"
    ): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("user_id", userId)
            put("listing_id", listingId)
            put("amount", amount)
            put("date", System.currentTimeMillis().toString())
            put("status", status)
        }
        val result = db.insert("payments", null, values)
        db.close()
        return result != -1L
    }

    fun getPaymentsForUser(userId: Int): List<Map<String, Any>> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM payments WHERE user_id=?", arrayOf(userId.toString()))
        val payments = mutableListOf<Map<String, Any>>()

        if (cursor.moveToFirst()) {
            do {
                payments.add(
                    mapOf(
                        "id" to cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        "listing_id" to cursor.getInt(cursor.getColumnIndexOrThrow("listing_id")),
                        "amount" to cursor.getDouble(cursor.getColumnIndexOrThrow("amount")),
                        "status" to cursor.getString(cursor.getColumnIndexOrThrow("status"))
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return payments
    }
}
