package com.example.domain

import com.example.domain.models.AuthorDetails
import com.example.domain.models.AuthorList

interface AuthorsRepository {
    suspend fun authors(query: String): AuthorList

    suspend fun author(authorId: String): AuthorDetails
}