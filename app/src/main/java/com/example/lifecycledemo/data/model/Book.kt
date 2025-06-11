package com.example.lifecycledemo.data.model

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    var yearPublished: Int,
    var isBorrowed: Boolean = false
)
