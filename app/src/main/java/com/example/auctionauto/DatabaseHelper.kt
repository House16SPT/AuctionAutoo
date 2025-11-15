package com.example.auctionauto

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "auctionauto.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        // users table
        db.execSQL("""
            CREATE TABLE users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                email TEXT UNIQUE,
                password TEXT,
                role TEXT
            )
        """)

        // listings table
        db.execSQL("""
            CREATE TABLE listings (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER,
                car_name TEXT,
                description TEXT,
                price REAL,
                tags TEXT,
                time_limit TEXT,
                FOREIGN KEY(user_id) REFERENCES users(id)
            )
        """)

        // bids table
        db.execSQL("""
            CREATE TABLE bids (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                listing_id INTEGER,
                user_id INTEGER,
                bid_amount REAL,
                bid_time TEXT,
                FOREIGN KEY(listing_id) REFERENCES listings(id),
                FOREIGN KEY(user_id) REFERENCES users(id)
            )
        """)

        // pyment table
        db.execSQL("""
            CREATE TABLE payments (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER,
                listing_id INTEGER,
                amount REAL,
                date TEXT,
                status TEXT,
                FOREIGN KEY(user_id) REFERENCES users(id),
                FOREIGN KEY(listing_id) REFERENCES listings(id)
            )
        """)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        db.execSQL("DROP TABLE IF EXISTS listings")
        db.execSQL("DROP TABLE IF EXISTS bids")
        db.execSQL("DROP TABLE IF EXISTS payments")
        onCreate(db)
    }
}
