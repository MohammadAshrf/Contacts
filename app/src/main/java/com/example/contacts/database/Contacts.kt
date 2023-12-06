package com.example.contacts.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Contacts")
data class Contacts(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val name: String,
    val number: String,

    @ColumnInfo(name = "insertion_timestamp")
    var timestamp: Long = 0
)
