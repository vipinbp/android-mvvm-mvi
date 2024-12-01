package com.example.mvvmmvisample.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mvvmmvisample.common.ErrorScreen
import com.example.mvvmmvisample.common.LoadingDialog
import com.example.mvvmmvisample.common.ScreenState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    uiState: ListUiState,
    onEvent: (ListEvent) -> Unit,
    uiEvent: SharedFlow<ListUiEvent>,
    onItemClick: (String) -> Unit
) {
    LaunchedEffect(key1 = uiEvent) {
        uiEvent.collect { event ->
            when (event) {
                is ListUiEvent.GoToDetails -> onItemClick(event.authorId)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Authors")
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        Box(modifier = Modifier.padding(innerPadding)) {
            when (uiState.state) {
                is ScreenState.Error -> ErrorScreen(message = uiState.state.message)
                is ScreenState.Loading -> LoadingDialog()
                is ScreenState.Success -> {
                    LazyColumn {
                        items(uiState.listUiItem) { item ->
                            ListItem(item = item) {
                                onEvent(ListEvent.GoToDetails(it.authorId))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ListItem(item: ListUiItem, onItemClick: (ListUiItem) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onItemClick(item)
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.title,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListScreen_Preview_Success() {
    ListScreen(uiState = ListUiState(state = ScreenState.Success, listUiItem = listOf()),
        onEvent = {},
        uiEvent = MutableSharedFlow<ListUiEvent>(),
        onItemClick = {})
}

@Preview(showBackground = true)
@Composable
fun ListScreen_Preview_Loading() {
    ListScreen(uiState = ListUiState(state = ScreenState.Loading, listUiItem = listOf()),
        onEvent = {},
        uiEvent = MutableSharedFlow<ListUiEvent>(),
        onItemClick = {})
}

@Preview(showBackground = true)
@Composable
fun ListScreen_Preview_Error() {
    ListScreen(uiState = ListUiState(state = ScreenState.Error("Error"), listUiItem = listOf()),
        onEvent = {},
        uiEvent = MutableSharedFlow<ListUiEvent>(),
        onItemClick = {})
}