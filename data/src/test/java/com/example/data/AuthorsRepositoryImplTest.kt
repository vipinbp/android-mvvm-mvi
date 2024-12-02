package com.example.data

import com.example.data.models.details.AuthorDetailsItem
import com.example.data.models.list.AuthorItem
import com.example.data.models.toAuthorDetailsItemEntity
import com.example.data.models.toAuthorItemEntity
import com.example.domain.models.AuthorDetails
import com.example.domain.models.AuthorList
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

class AuthorsRepositoryImplTest {

    @MockK
    private lateinit var apiService: APIService

    private lateinit var authorsRepositoryImpl: AuthorsRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        authorsRepositoryImpl = AuthorsRepositoryImpl(apiService)
    }

    @Test
    fun `authors should return AuthorList Success when apiService returns data`() = runBlocking {
        // Arrange
        val query = "query"
        val authorsResponse = AuthorItem(numFound = 1L, start = 0L, numFoundExact = true, docs = listOf(), )
        coEvery { apiService.authors(query, any()) } returns authorsResponse

        // Act
        val result = authorsRepositoryImpl.authors(query)

        // Assert
        assertEquals(AuthorList.Success(authorsResponse.toAuthorItemEntity()), result)
    }

    @Test
    fun `authors should return AuthorList Error when apiService throws exception`() = runBlocking {
        // Arrange
        val query = "query"
        val exception = IOException("Network error")
        coEvery { apiService.authors(query, any()) } throws exception

        // Act
        val result = authorsRepositoryImpl.authors(query)

        // Assert
        assertEquals(AuthorList.Error(exception.message.toString()), result)
    }

    @Test
    fun `authors should return AuthorList Error when apiService returns null`() = runBlocking {
        // Arrange
        val query = "query"
        coEvery { apiService.authors(query, any()) } returns null

        // Act
        val result = authorsRepositoryImpl.authors(query)

        // Assert
        assertEquals(AuthorList.Error("No data found"), result)
    }

    @Test
    fun `author should return AuthorDetails Success when apiService returns data`() = runBlocking {
        // Arrange
        val authorId = "123"
        val authorDetailsResponse = mockk<AuthorDetailsItem>(relaxed = true)
            coEvery { apiService.author(authorId) } returns authorDetailsResponse

        // Act
        val result = authorsRepositoryImpl.author(authorId)

        // Assert
        assertEquals(
            AuthorDetails.Success(authorDetailsResponse.toAuthorDetailsItemEntity(authorId)),
            result
        )
    }

    @Test
    fun `author should return AuthorDetails Error when apiService throws exception`() =
        runBlocking {
            // Arrange
            val authorId = "123"
            val exception = IOException("Network error")
            coEvery { apiService.author(authorId) } throws exception

            // Act
            val result = authorsRepositoryImpl.author(authorId)

            // Assert
            assertEquals(AuthorDetails.Error(exception.message.toString()), result)
        }

    @Test
    fun `author should return AuthorDetails Error when apiService returns null`() = runBlocking {
        // Arrange
        val authorId = "123"
        coEvery { apiService.author(authorId) } returns null

        // Act
        val result = authorsRepositoryImpl.author(authorId)

        // Assert
        assertEquals(AuthorDetails.Error("No data found"), result)
    }
}