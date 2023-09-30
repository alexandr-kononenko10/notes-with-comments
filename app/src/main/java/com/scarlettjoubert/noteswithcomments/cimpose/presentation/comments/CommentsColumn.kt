@file:OptIn(ExperimentalMaterialApi::class)

package com.scarlettjoubert.noteswithcomments.cimpose.presentation.comments

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.scarlettjoubert.noteswithcomments.R
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.showsnakebar.showSnackbar
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.swipeelements.DismissBackground
import com.scarlettjoubert.noteswithcomments.domain.entity.Comment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsColumn(
    comments: List<Comment>,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    paddingValues: PaddingValues,
    onItemClick: (Comment) -> Unit,
    viewModel: CommentScreenViewModel,
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 12.dp,
            end = 12.dp,
            bottom = 72.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = comments,
            key = { it.id }
        ) {
            val dismissState = rememberDismissState()
            val message = stringResource(id = R.string.deleted_snakbar)
            val actionText = stringResource(id = R.string.deleted_snakbar_action)

            if (dismissState.isDismissed(DismissDirection.EndToStart) || dismissState.isDismissed(
                    DismissDirection.StartToEnd
                )
            ) {
                scope.launch {
                    showSnackbar(
                        item = it,
                        saveComment = { viewModel.saveComment(it) },
                        state = snackbarHostState,
                        message = message,
                        actionText = actionText,
                        deleteCommentAction = { viewModel.delete(it) },
                        dismissState = dismissState
                    )
                }
            }

            SwipeToDismiss(state = dismissState, background = {
                DismissBackground(dismissState = dismissState)
            },
                dismissContent = {
                    CommentItem(
                        comment = it,
                        onItemClick = { comment ->
                            onItemClick(comment)
                        }
                    )
                })


        }
    }
}