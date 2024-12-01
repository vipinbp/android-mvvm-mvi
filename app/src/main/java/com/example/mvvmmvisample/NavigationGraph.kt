package com.example.mvvmmvisample

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mvvmmvisample.details.DetailsScreen
import com.example.mvvmmvisample.details.DetailsViewModel
import com.example.mvvmmvisample.list.ListScreen
import com.example.mvvmmvisample.list.ListViewModel
import kotlinx.serialization.Serializable

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ListScreenRoute) {
        composable<ListScreenRoute> {
            val viewModel: ListViewModel = hiltViewModel()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            ListScreen(uiState.value){
                navController.navigate(DetailsScreenRoute)
            }
        }
        composable<DetailsScreenRoute> {
            val viewModel: DetailsViewModel = hiltViewModel()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            DetailsScreen(uiState.value)
        }
    }
}

@Serializable
data object ListScreenRoute
@Serializable
data object DetailsScreenRoute