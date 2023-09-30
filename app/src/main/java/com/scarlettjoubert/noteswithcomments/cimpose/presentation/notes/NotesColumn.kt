@file:OptIn(ExperimentalMaterialApi::class)

package com.scarlettjoubert.noteswithcomments.cimpose.presentation.notes

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.scarlettjoubert.noteswithcomments.R
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.showsnakebar.showSnackbar
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.swipeelements.DismissBackground
import com.scarlettjoubert.noteswithcomments.domain.entity.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalMaterial3Api
@Composable
fun NotesColumn(
    notes: List<Note>,
    paddingValues: PaddingValues,
    onItemClick: (Note) -> Unit,
    lazyListState: LazyListState,
    deleteNote: (Note) -> Unit,
    saveNote: (Note) -> Unit,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .imePadding(),
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 12.dp,
            end = 12.dp,
            bottom = 72.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = lazyListState
    ) {
        items(
            items = notes,
            key = { it.id ?: throw IllegalArgumentException() }
        ) { note ->

            val dismissState = rememberDismissState(initialValue = DismissValue.Default,
                positionalThreshold = {
                    150.dp.toPx()
                })
            val message = stringResource(id = R.string.deleted_snakbar)
            val actionText = stringResource(id = R.string.deleted_snakbar_action)

            if (dismissState.isDismissed(DismissDirection.EndToStart) || dismissState.isDismissed(
                    DismissDirection.StartToEnd
                )
            ) {
                scope.launch {
                    showSnackbar(
                        item = note,
                        saveNte = saveNote,
                        state = snackbarHostState,
                        message = message,
                        actionText = actionText,
                        deleteNoteAction = deleteNote,
                        dismissState = dismissState
                    )
                }
            }


            SwipeToDismiss(
                state = dismissState,
                background = {
                    DismissBackground(dismissState = dismissState)
                },
                dismissContent = {
                    NoteItem(
                        topic = note.topic,
                        text = note.text,
                        created = note.created,
                        onItemClick = { onItemClick(note) }
                    )
                },
                directions = setOf(DismissDirection.EndToStart, DismissDirection.StartToEnd),
            )

        }
    }
}

