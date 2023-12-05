package com.example.contacts.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Courses")
data class Contacts(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val name: String,
    val number: Int?= null
)
