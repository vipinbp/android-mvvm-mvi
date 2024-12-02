package com.example.domain.usecase

import com.example.domain.AuthorsRepository
import com.example.domain.models.AuthorItemEntity
import com.example.domain.models.AuthorList
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetAuthorsUseCaseTest {

    @MockK
    private lateinit var authorsRepository: AuthorsRepository

    private lateinit var getAuthorsUseCase: GetAuthorsUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        getAuthorsUseCase = GetAuthorsUseCase(authorsRepository)
    }

    @Test
    fun `invoke should return AuthorList Success when repository returns Success`() = runBlocking {
        // Arrange
        val query = "query"
        val authors = listOf(
            AuthorItemEntity("1", "Author 1", "Subject 1", "image1"),
            AuthorItemEntity("2", "Author 2", "Subject 2", "image2")
        )
        val authorListSuccess = AuthorList.Success(authors)
        coEvery { authorsRepository.authors(query) } returns authorListSuccess

        // Act
        val result = getAuthorsUseCase(query)

        // Assert
        assertEquals(authorListSuccess, result)
    }

    @Test
    fun `invoke should return AuthorList Error when repository returns Error`() = runBlocking {
        // Arrange
        val query = "query"
        val errorMessage = "Error message"
        val authorListError = AuthorList.Error(errorMessage)
        coEvery { authorsRepository.authors(query) } returns authorListError

        // Act
        val result = getAuthorsUseCase(query)

        // Assert
        assertEquals(authorListError, result)
    }
}