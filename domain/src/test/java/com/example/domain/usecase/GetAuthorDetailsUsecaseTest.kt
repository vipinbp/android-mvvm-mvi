package com.example.domain.usecase

import com.example.domain.AuthorsRepository
import com.example.domain.models.AuthorDetails
import com.example.domain.models.AuthorDetailsItemEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetAuthorDetailsUseCaseTest {

    @MockK
    private lateinit var authorsRepository: AuthorsRepository

    private lateinit var getAuthorDetailsUseCase: GetAuthorDetailsUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getAuthorDetailsUseCase = GetAuthorDetailsUseCase(authorsRepository)
    }

    @Test
    fun `invoke should return AuthorDetails Success when repository returns Success`() = runBlocking {
        // Arrange
        val authorId = "123"
        val authorDetails = AuthorDetails.Success(
            AuthorDetailsItemEntity(
                authorId,
                "Author Name",
                "Author Description",
                "image_url"
            )
        )
        coEvery { authorsRepository.author(authorId) } returns authorDetails

        // Act
        val result = getAuthorDetailsUseCase(authorId)

        // Assert
        assertEquals(authorDetails, result)
    }

    @Test
    fun `invoke should return AuthorDetails Error when repository returns Error`() = runBlocking {
        // Arrange
        val authorId = "123"
        val errorMessage = "Error message"
        val authorDetailsError = AuthorDetails.Error(errorMessage)
        coEvery { authorsRepository.author(authorId) } returns authorDetailsError

        // Act
        val result = getAuthorDetailsUseCase(authorId)

        // Assert
        assertEquals(authorDetailsError, result)
    }
}