package com.scarlettjoubert.noteswithcomments.cimpose.presentation.searchingscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scarlettjoubert.noteswithcomments.domain.entity.Note
import com.scarlettjoubert.noteswithcomments.domain.usecases.DeleteNoteUseCase
import com.scarlettjoubert.noteswithcomments.domain.usecases.SaveNoteUseCase
import com.scarlettjoubert.noteswithcomments.domain.usecases.SearchNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchingScreenViewModel @Inject constructor(
    private val searchNoteUseCase: SearchNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val saveNoteUseCase: SaveNoteUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow<SearchingScreenState>(SearchingScreenState.Initial as SearchingScreenState)
    val state = _state.asStateFlow()

    fun findNote(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) _state.value = SearchingScreenState.Initial
            else {
                searchNoteUseCase(query).collect { resultsList ->
                    when (resultsList.size) {
                        0 -> _state.value = SearchingScreenState.NoResults
                        else -> {
                            _state.value = SearchingScreenState.NoteSearching(
                                query = query,
                                results = resultsList
                            )
                        }
                    }
                }
            }
        }
    }

    fun delete(note:Note){
        deleteNoteUseCase(note)
    }

    fun saveNote(note: Note){
        saveNoteUseCase(note)
    }
}