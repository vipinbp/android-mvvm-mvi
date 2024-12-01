package com.example.data

import com.example.data.models.details.AuthorDetailsItem
import com.example.data.models.list.AuthorItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("/search/authors.json")
    suspend fun authors(
        @Query("q") q: String,
        @Query("limit") maxResults: Int
    ) : AuthorItem?

    @GET("/authors/{authorId}.json")
    suspend fun author(
        @Path("authorId") authorId: String
    ) : AuthorDetailsItem?
}