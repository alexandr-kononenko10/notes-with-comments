package com.scarlettjoubert.noteswithcomments.cimpose.presentation.showsnakebar

import android.util.Log
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import com.scarlettjoubert.noteswithcomments.domain.entity.Comment
import com.scarlettjoubert.noteswithcomments.domain.entity.Note
import com.scarlettjoubert.noteswithcomments.domain.entity.SavingItem

@ExperimentalMaterial3Api
suspend fun showSnackbar(
    item: SavingItem,
    saveNte: ((Note) -> Unit)? = null,
    saveComment: ((Comment) -> Unit)? = null,
    state: SnackbarHostState,
    message: String,
    actionText: String,
    deleteNoteAction: ((Note) -> Unit)? = null,
    deleteCommentAction: ((Comment) -> Unit)? = null,
    dismissState: DismissState,
    ) {

    when(item){
        is Note -> deleteNoteAction?.invoke(item)
        is Comment -> deleteCommentAction?.invoke(item)
    }

    dismissState.snapTo(DismissValue.Default)

    val result = state.showSnackbar(
        message =  message,
        actionLabel = actionText,
        duration = SnackbarDuration.Short
    )

    when (result) {
        SnackbarResult.Dismissed -> {}

        SnackbarResult.ActionPerformed -> {
            when (item) {
                is Note -> saveNte?.invoke(item)
                is Comment -> saveComment?.invoke(item)
            }
        }
    }
}