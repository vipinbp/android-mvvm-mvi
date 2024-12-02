package com.example.mvvmmvisample.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.testIn
import com.example.domain.models.AuthorItemEntity
import com.example.domain.models.AuthorList
import com.example.domain.usecase.GetAuthorsUseCase
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
class ListViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var getAuthorsUseCase: GetAuthorsUseCase

    private lateinit var viewModel: ListViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchAuthors success`() = runTest {
        // Arrange
        val authors = listOf(
            AuthorItemEntity("1", "Author 1", "Subject 1", "image1"),
            AuthorItemEntity("2", "Author 2", "Subject 2", "image2")
        )
        coEvery { getAuthorsUseCase(query = any()) } returns AuthorList.Success(authors)
        viewModel = ListViewModel(getAuthorsUseCase)

        // Act
        // Initialization in the ViewModel constructor calls fetchAuthors
        // No action needed here

        // Assert
        viewModel.uiState.testIn(this).apply {
            awaitItem()
            val uiState = awaitItem()
            assertEquals(ScreenState.Success, uiState.state)
            assertEquals(
                authors.map {
                    ListUiItem(
                        it.authorId,
                        it.name,
                        it.topSubjects,
                        it.profileImage
                    )
                },
                uiState.listUiItem
            )
            cancel()
        }
    }

    @Test
    fun `fetchAuthors error`() = runTest {
        // Arrange
        val errorMessage = "Error message"
        coEvery { getAuthorsUseCase(query = any()) } returns AuthorList.Error(errorMessage)
        viewModel = ListViewModel(getAuthorsUseCase)

        // Act
        // Initialization in the ViewModel constructor calls fetchAuthors
        // No action needed here
        viewModel.uiState.testIn(this).apply {
            awaitItem()
            val uiState = awaitItem()
            // Assert
            assertEquals(ScreenState.Error(errorMessage), uiState.state)
            cancel()
        }
    }

    @Test
    fun `onEvent GoToDetails`() = runTest {
        coEvery { getAuthorsUseCase(query = any()) } returns AuthorList.Error("errorMessage")
        viewModel = ListViewModel(getAuthorsUseCase)
        // Arrange
        val authorId = "123"

        // Act
        viewModel.onEvent(ListEvent.GoToDetails(authorId))

        // Assert
        viewModel.uiEvent.testIn(this).apply {
            val uiEvent = awaitItem()
            // Assert
            assertEquals(ListUiEvent.GoToDetails(authorId), uiEvent)
            cancel()
        }
    }
}