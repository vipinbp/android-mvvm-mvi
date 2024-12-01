package com.example.data

import com.example.data.models.toAuthorDetailsItemEntity
import com.example.data.models.toAuthorItemEntity
import com.example.domain.AuthorsRepository
import com.example.domain.models.AuthorDetails
import com.example.domain.models.AuthorList
import javax.inject.Inject

class AuthorsRepositoryImpl @Inject constructor(val apiService: APIService) : AuthorsRepository {

    override suspend fun authors(
        query: String
    ): AuthorList {
        try {
            val authors = apiService.authors(query, MAX_RESULTS)?.toAuthorItemEntity()
            return if (authors != null) AuthorList.Success(authors)
            else AuthorList.Error("No data found")
        } catch (e: Exception) {
            return AuthorList.Error(e.message.toString())
        }
    }

    override suspend fun author(authorId: String): AuthorDetails {
        try {
            val authors = apiService.author(authorId)?.toAuthorDetailsItemEntity(authorId)
            return if (authors != null) AuthorDetails.Success(authors)
            else AuthorDetails.Error("No data found")
        } catch (e: Exception) {
            return AuthorDetails.Error(e.message.toString())
        }
    }

    companion object {
        private const val MAX_RESULTS = 20
    }
}