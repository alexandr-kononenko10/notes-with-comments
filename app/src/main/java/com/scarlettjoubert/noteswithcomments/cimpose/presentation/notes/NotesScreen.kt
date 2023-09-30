package com.scarlettjoubert.noteswithcomments.cimpose.presentation.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.SnackbarHost
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.scarlettjoubert.noteswithcomments.R
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.home.TopBarHome
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.ui.theme.Yellow
import com.scarlettjoubert.noteswithcomments.domain.entity.Note
import kotlinx.coroutines.CoroutineScope

@ExperimentalMaterial3Api
@Composable
fun NotesScreen(
    viewModel: NotesViewModel,
    paddingValues: PaddingValues,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    onSearchLineClick: () -> Unit,
    onItemClick: (Note) -> Unit,
    onFABClick: () -> Unit,
) {

    val screenState = viewModel.screenState.collectAsState(initial = NotesScreenState.Initial)

    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopBarHome(
                onSearchLineClick = { onSearchLineClick() },
                title = R.string.title_notes
            )
        },

        floatingActionButton = {
            SmallFloatingActionButton(
                modifier = Modifier
                    .padding(bottom = 70.dp)
                    .size(45.dp),
                onClick = { onFABClick() },
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
        }
    ) { paddingValues ->
        when (val currentState = screenState.value) {
            is NotesScreenState.Initial -> {}
            is NotesScreenState.AllNotes -> {
                NotesColumn(
                    notes = currentState.notes,
                    paddingValues = paddingValues,
                    onItemClick = onItemClick,
                    lazyListState = listState,
                    deleteNote = { note ->
                        viewModel.delete(note)
                    },
                    saveNote = { note ->
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

