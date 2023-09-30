package com.scarlettjoubert.noteswithcomments.cimpose.presentation.searchingscreen


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.scarlettjoubert.noteswithcomments.R
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.notes.NotesColumn
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.ui.theme.Yellow
import com.scarlettjoubert.noteswithcomments.domain.entity.Note
import kotlinx.coroutines.CoroutineScope


@ExperimentalMaterial3Api
@Composable
fun SearchingScreen(
    viewModel: SearchingScreenViewModel,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    onItemClick: (Note) -> Unit,
    onBackPressed:() -> Unit
) {
    val listState = rememberLazyListState()

    val state = viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            SearchScreenTopBar(onBackPressed = onBackPressed, viewModel = viewModel)
        }

    ) { paddingValues ->

        when (val currentState = state.value) {
            is SearchingScreenState.Initial -> {
                InitialSearchScreen(paddingValues)
            }

            is SearchingScreenState.NoResults -> {
                NoResultsScreen(paddingValues)
            }

            is SearchingScreenState.NoteSearching -> {
                NotesColumn(
                    notes = currentState.results,
                    paddingValues = paddingValues,
                    onItemClick = onItemClick,
                    lazyListState = listState,
                    deleteNote = {note ->
                        viewModel.delete(note)
                    },
                    saveNote = {note ->
                        viewModel.saveNote(note)
                    },
                    snackbarHostState = snackbarHostState,
                    scope = scope
                )
            }
        }
    }
}

@Composable
fun NoResultsScreen(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(18.dp)
    ) {
        Spacer(
            modifier = Modifier
                .height(18.dp)
        )
        Text(
            text = stringResource(id = R.string.no_results),
            fontSize = 17.sp,
            color = Yellow
        )
    }
}

@Composable
fun InitialSearchScreen(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(18.dp)
    ) {
        Spacer(
            modifier = Modifier
                .height(18.dp)
        )
        Text(
            text = stringResource(id = R.string.initial_search_text),
            fontSize = 17.sp,
            color = Yellow
        )
    }
}



