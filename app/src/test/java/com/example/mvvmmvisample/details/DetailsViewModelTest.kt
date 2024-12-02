package com.example.mvvmmvisample.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.domain.models.AuthorDetails
import com.example.domain.models.AuthorDetailsItemEntity
import com.example.domain.usecase.GetAuthorDetailsUseCase
import com.example.mvvmmvisample.common.ScreenState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var getAuthorDetailsUsecase: GetAuthorDetailsUseCase

    private lateinit var viewModel: DetailsViewModel

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        savedStateHandle = SavedStateHandle(mapOf("authorId" to "123"))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchAuthorDetails success`() = runTest {
        viewModel = DetailsViewModel(savedStateHandle, getAuthorDetailsUsecase)
        // Arrange
        val authorDetails = AuthorDetails.Success(
            AuthorDetailsItemEntity(
                "123",
                "Author Name",
                "Author Description",
                "image_url"
            )
        )
        coEvery { getAuthorDetailsUsecase(authorId = "123") } returns authorDetails

        // Act
        // Initialization in the ViewModel constructor calls fetchAuthorDetails
        // No action needed here

        // Assert
        viewModel.uiState.test {
            assertEquals(DetailsUiState(state = ScreenState.Loading), awaitItem()) // Initial loading state

            val successState = awaitItem()
            assertEquals(ScreenState.Success, successState.state)
            assertEquals("Author Name", successState.title)
            assertEquals("Author Description", successState.description)
            assertEquals("image_url", successState.imageUrl)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchAuthorDetails error`() = runTest {
        viewModel = DetailsViewModel(savedStateHandle, getAuthorDetailsUsecase)
        // Arrange
        val errorMessage = "Error message"
        coEvery { getAuthorDetailsUsecase(authorId = "123") } returns AuthorDetails.Error(errorMessage)

        // Act
        // Initialization in the ViewModel constructor calls fetchAuthorDetails
        // No action needed here

        // Assert
        viewModel.uiState.test {
            assertEquals(DetailsUiState(state = ScreenState.Loading), awaitItem()) // Initial loading state

            val errorState = awaitItem()
            assertEquals(ScreenState.Error(errorMessage), errorState.state)

            cancelAndIgnoreRemainingEvents()
        }
    }
}