package com.scarlettjoubert.noteswithcomments.cimpose.presentation.notesfromtopic

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.scarlettjoubert.noteswithcomments.R
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.notes.NotesColumn
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.ui.theme.Yellow
import com.scarlettjoubert.noteswithcomments.domain.entity.Note
import kotlinx.coroutines.CoroutineScope

@ExperimentalMaterial3Api
@Composable
fun NotesFromTopic(
    viewModel: NotesFromTopicViewModel,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    onSearchLineClick: () -> Unit,
    onBackPressed: () -> Unit,
    onItemClick: (Note) -> Unit,
) {
    val screenState = viewModel.screenState.collectAsState(NotesFromTopicScreenState.Initial)

    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.title_notes_from_topic),
                        color = Yellow
                    )
                },

                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Yellow
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { onSearchLineClick() }) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp),
                            tint = Yellow
                        )
                    }

                }
            )
        }
    ) { paddingValues ->
        when (val currentState = screenState.value) {
            is NotesFromTopicScreenState.Initial -> {}

            is NotesFromTopicScreenState.NotesFromTopic -> {
                NotesColumn(
                    notes = currentState.notes,
                    paddingValues = paddingValues,
                    onItemClick = {
                        onItemClick(it)
                    },
                    lazyListState = listState,
                    deleteNote = { note ->
                        viewModel.delete(note)
                    },
                    saveNote = { note: Note ->
                        viewModel.saveNote(note)
                    },
                    snackbarHostState = snackbarHostState,
                    scope = scope
                )
            }

            else -> {}
        }

    }
}