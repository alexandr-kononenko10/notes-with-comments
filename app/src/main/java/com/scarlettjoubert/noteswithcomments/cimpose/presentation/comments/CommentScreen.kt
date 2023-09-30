package com.scarlettjoubert.noteswithcomments.cimpose.presentation.comments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.ui.theme.Yellow
import com.scarlettjoubert.noteswithcomments.domain.entity.Comment
import kotlinx.coroutines.CoroutineScope

@ExperimentalMaterial3Api
@Composable
fun CommentScreen(
    viewModel: CommentScreenViewModel,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    onBackPressed: () -> Unit,
    onFABClick: (Int) -> Unit,
    onItemClick:(Comment) -> Unit
) {

    val state = viewModel.state.collectAsState(CommentScreenState.Initial)

    Scaffold(
        topBar = {
            CommentsTopBar {
                onBackPressed()
            }
        },
        floatingActionButton = {
            SmallFloatingActionButton(
                modifier = Modifier
                    .padding(bottom = 70.dp)
                    .size(45.dp),
                onClick = { onFABClick(viewModel.noteId) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add, contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Yellow)
                        .size(41.dp)
                )
            }
        },
    ) { paddingValues ->
        when (val currentState = state.value) {
            is CommentScreenState.Initial -> {}
            is CommentScreenState.Comments -> {
               CommentsColumn(
                   comments =currentState.comments,
                   paddingValues = paddingValues,
                   onItemClick = onItemClick,
                   viewModel = viewModel,
                   scope = scope,
                   snackbarHostState = snackbarHostState
               )
            }

            else -> {}
        }

    }
}
