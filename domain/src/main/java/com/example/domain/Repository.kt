package com.example.domain

//import com.example.data.models.list.AuthorItem
import com.example.domain.models.AuthorDetailsItemEntity
import com.example.domain.models.AuthorItemEntity
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun authors(query: String,
                maxResults: Int): Flow<List<AuthorItemEntity>?>

    fun author(authorId: String): Flow<AuthorDetailsItemEntity?>
}