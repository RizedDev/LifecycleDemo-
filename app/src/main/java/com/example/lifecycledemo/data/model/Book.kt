package com.example.lifecycledemo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val title: String,
    val author: String,
    var yearPublished: Int,
    var isBorrowed: Boolean = false
)
