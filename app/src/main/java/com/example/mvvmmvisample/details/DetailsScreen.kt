package com.example.mvvmmvisample.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mvvmmvisample.common.LoadingDialog

@Composable
fun DetailsScreen(uiState: DetailsUiState) {

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        Box(modifier = Modifier.padding(innerPadding)) {
            if (uiState.loading) {
                LoadingDialog()
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
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

@Preview(showBackground = true)
@Composable
fun DetailsScreen_Preview() {
    DetailsScreen(
        uiState = DetailsUiState(
            loading = true,
            title = "Title",
            description = "Description",
            imageUrl = ""
        )
    )
}