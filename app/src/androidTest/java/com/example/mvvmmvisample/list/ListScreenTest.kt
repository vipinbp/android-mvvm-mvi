package com.example.mvvmmvisample.list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.mvvmmvisample.common.ScreenState
import kotlinx.coroutines.flow.MutableSharedFlow
import org.junit.Rule
import org.junit.Test

class ListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testListScreen_SuccessState() {

        // Arrange
        val uiState = ListUiState(state = ScreenState.Success, listUiItem = listOf())

        // Act
        composeTestRule.setContent {
            ListScreen(
                uiState = uiState,
                onEvent = {},
                uiEvent = MutableSharedFlow(),
                onItemClick = {}
            )
        }

        // Assert
        composeTestRule.onNodeWithText("Authors")
            .assertIsDisplayed() // Check if TopAppBar is displayed
    }

    @Test
    fun testListScreen_LoadingState() {
        // Arrange
        val uiState = ListUiState(state = ScreenState.Loading, listUiItem = listOf())

        // Act
        composeTestRule.setContent {
            ListScreen(
                uiState = uiState,
                onEvent = {},
                uiEvent = MutableSharedFlow(),
                onItemClick = {}
            )
        }

        // Assert
        composeTestRule.onNodeWithText("Loading...")
            .assertIsDisplayed() // Check if error message is displayed
    }

    @Test
    fun testListScreen_ErrorState() {
        // Arrange
        val uiState = ListUiState(state = ScreenState.Error("Error"), listUiItem = listOf())

        // Act
        composeTestRule.setContent {
            ListScreen(
                uiState = uiState,
                onEvent = {},
                uiEvent = MutableSharedFlow(),
                onItemClick = {}
            )
        }

        // Assert
        composeTestRule.onNodeWithText("Error")
            .assertIsDisplayed() // Check if error message is displayed
    }
}