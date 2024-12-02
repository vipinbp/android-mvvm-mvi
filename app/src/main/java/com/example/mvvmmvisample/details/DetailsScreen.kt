package com.example.mvvmmvisample.details

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.mvvmmvisample.common.ErrorScreen
import com.example.mvvmmvisample.common.LoadingDialog
import com.example.mvvmmvisample.common.ScreenState
import com.example.mvvmmvisample.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(uiState: DetailsUiState, navController: NavHostController) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(stringResource(R.string.author_details))
                },
                navigationIcon = {
                    androidx.compose.material3.IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ){
                        androidx.compose.material3.Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()) { innerPadding ->

        Box(modifier = Modifier.padding(innerPadding)) {
            when (uiState.state) {
                is ScreenState.Error -> ErrorScreen(message = uiState.state.message)
                is ScreenState.Loading -> LoadingDialog()
                is ScreenState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .scrollable(state = rememberScrollState(), orientation = Orientation.Vertical)
                            .padding(16.dp)
                    ) {
                        AsyncImage(
                            model = uiState.imageUrl,
                            contentDescription = uiState.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = uiState.title,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = uiState.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreen_Preview() {
    DetailsScreen(
        uiState = DetailsUiState(
            state = ScreenState.Success,
            title = "Title",
            description = "Description",
            imageUrl = ""
        ),
        navController = NavHostController(LocalContext.current)
    )
}