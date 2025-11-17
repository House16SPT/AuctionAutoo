package com.example.auctionauto.data

import android.content.Context

class UserRepository(context: Context) {

    private val userDao = AppDatabase.getDatabase(context).userDao()

    // Login
    suspend fun loginUser(email: String, password: String): Boolean {
        val user = userDao.getUserByEmail(email)
        return user != null && user.password == password
    }

    // Register
    // returns true if created; false if email already exists
    suspend fun registerUser(name: String, email: String, password: String): Boolean {
        val existing = userDao.getUserByEmail(email)
        if (existing != null) return false

        userDao.insertUser(User(name = name, email = email, password = password))
        return true
    }
}
