package com.example.data

import com.example.data.models.toAuthorDetailsItemEntity
import com.example.data.models.toAuthorItemEntity
import com.example.domain.Repository
import com.example.domain.models.AuthorDetailsItemEntity
import com.example.domain.models.AuthorItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(val aPIService: APIService) : Repository {

    override fun authors(
        query: String,
        maxResults: Int
    ): Flow<List<AuthorItemEntity>?> =
        flow {
            emit(aPIService.authors(query, maxResults)?.toAuthorItemEntity())
        }

    override fun author(authorId: String): Flow<AuthorDetailsItemEntity?> =
        flow {
            emit(aPIService.author(authorId)?.toAuthorDetailsItemEntity())
        }
}