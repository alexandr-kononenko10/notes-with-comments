package com.scarlettjoubert.noteswithcomments.cimpose.presentation.notes

import androidx.lifecycle.ViewModel
import com.scarlettjoubert.noteswithcomments.domain.entity.Note
import com.scarlettjoubert.noteswithcomments.domain.usecases.CreateNoteUseCase
import com.scarlettjoubert.noteswithcomments.domain.usecases.DeleteNoteUseCase
import com.scarlettjoubert.noteswithcomments.domain.usecases.GetAllNoteDescUseCase
import com.scarlettjoubert.noteswithcomments.domain.usecases.SaveNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNoteDescUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val saveNoteUseCase: SaveNoteUseCase
) : ViewModel() {

    val screenState = getAllNotesUseCase()
        .filter { it.isNotEmpty() }
        .map { NotesScreenState.AllNotes(it) as NotesScreenState }
        .onStart { emit(NotesScreenState.Initial) }

    fun delete(note: Note) {
        deleteNoteUseCase(note)
    }

    fun saveNote(note: Note){
        saveNoteUseCase(note)
    }

}