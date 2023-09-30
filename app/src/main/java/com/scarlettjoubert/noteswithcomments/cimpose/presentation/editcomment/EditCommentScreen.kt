package com.scarlettjoubert.noteswithcomments.cimpose.presentation.editcomment

import androidx.activity.compose.BackHandler
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext


@ExperimentalMaterial3Api
@Composable
fun EditCommentScreen(
    viewModel: EditCommentViewModel,
    onBackPressed: () -> Unit,
) {
    val state = viewModel.state.collectAsState(EditCommentScreenState.Initial)
    val context = LocalContext.current

    BackHandler {
        viewModel.onBackPressed {
            onBackPressed()
        }
    }

    Scaffold(
        topBar = {
            TopBarEditComment(
                onBackPressed = {
                    viewModel.onBackPressed {
                        onBackPressed()
                    }
                },
                onDeletePressed = {
                    viewModel.deleteComment()
                    onBackPressed()
                },
                onSharePressed = {
                    viewModel.shareComment(context)
                })
        }
    ) { paddingValues ->
        when (val currentState = state.value) {
            is EditCommentScreenState.Initial -> {}
            else -> {
                EditCommentContent(
                    viewModel = viewModel,
                    paddingValues = paddingValues,
                    state = currentState
                )
            }
        }
    }
}



