package com.example.auctionauto

import android.content.ContentValues
import android.content.Context

class UserRepository(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun registerUser(name: String, email: String, password: String, role: String = "buyer"): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("name", name)
            put("email", email)
            put("password", password)
            put("role", role)
        }
        val result = db.insert("users", null, values)
        db.close()
        return result != -1L
    }

    fun loginUser(email: String, password: String): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM users WHERE email=? AND password=?",
            arrayOf(email, password)
        )
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    fun checkEmailExists(email: String): Boolean {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT id FROM users WHERE email=?", arrayOf(email))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    fun getUserId(email: String): Int? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT id FROM users WHERE email=?", arrayOf(email))
        val id = if (cursor.moveToFirst()) cursor.getInt(0) else null
        cursor.close()
        db.close()
        return id
    }
}
