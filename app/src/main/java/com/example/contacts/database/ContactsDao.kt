package com.example.contacts.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ContactsDao {
    @Insert
    fun insertContact(contact: Contacts)

    @Query("SELECT * FROM Contacts ORDER BY insertion_timestamp ASC")
    fun getAllContacts(): List<Contacts>

}