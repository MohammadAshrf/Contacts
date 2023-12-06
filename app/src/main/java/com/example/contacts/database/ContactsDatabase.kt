package com.example.contacts.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contacts::class], version = 3)
abstract class ContactsDatabase : RoomDatabase() {
    abstract fun getContactsDao(): ContactsDao

    companion object {
        private var databaseInstance: ContactsDatabase? = null

        fun getInstance(context: Context): ContactsDatabase {
            if (databaseInstance == null)
                databaseInstance =
                    Room.databaseBuilder(context, ContactsDatabase::class.java, "ContactsDB")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
            return databaseInstance!!
        }
    }

}