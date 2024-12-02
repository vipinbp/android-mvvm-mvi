package com.example.mvvmmvisample.details

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.example.mvvmmvisample.common.ScreenState
import org.junit.Rule
import org.junit.Test

class DetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testDetailsScreen_SuccessState() {
        // Arrange
        val uiState = DetailsUiState(
            state = ScreenState.Success,
            title = "Title",
            description = "Description",
            imageUrl = ""
        )

        // Act
        composeTestRule.setContent {
            val navController = rememberNavController() // or mock NavController
            DetailsScreen(uiState = uiState, navController = navController)
        }

        // Assert
        composeTestRule.onNodeWithText("Author Details").assertIsDisplayed() // Check if TopAppBar is displayed
        composeTestRule.onNodeWithText("Title").assertIsDisplayed() // Check if title is displayed
        composeTestRule.onNodeWithText("Description").assertIsDisplayed() // Check if description is displayed
    }

    @Test
    fun testDetailsScreen_LoadingState() {
        // Arrange
        val uiState = DetailsUiState(state = ScreenState.Loading)

        // Act
        composeTestRule.setContent {
            val navController = rememberNavController() // or mock NavController
            DetailsScreen(uiState = uiState, navController = navController)
        }

        // Assert
        composeTestRule.onNodeWithText("Loading...").assertIsDisplayed() //Check if loading message is displayed
    }

    @Test
    fun testDetailsScreen_ErrorState() {
        // Arrange
        val uiState = DetailsUiState(state = ScreenState.Error("Error"))

        // Act
        composeTestRule.setContent {
            val navController = rememberNavController() // or mock NavController
            DetailsScreen(uiState = uiState, navController = navController)
        }

        // Assert
        composeTestRule.onNodeWithText("Error").assertIsDisplayed() // Check if error message is displayed
    }
}