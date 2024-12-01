package com.example.data.models.list

data class AuthorItem(
    val numFound: Long,
    val start: Long,
    val numFoundExact: Boolean,
    val docs: List<Doc>,
)