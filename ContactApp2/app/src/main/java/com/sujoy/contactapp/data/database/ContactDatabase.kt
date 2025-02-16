package com.sujoy.contactapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 3, exportSchema = true)
abstract class ContactDatabase : RoomDatabase() {
    abstract val dao: Dao
}