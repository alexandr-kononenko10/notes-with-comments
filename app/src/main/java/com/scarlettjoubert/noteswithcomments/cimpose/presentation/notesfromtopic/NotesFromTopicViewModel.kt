package com.scarlettjoubert.noteswithcomments.cimpose.presentation.notesfromtopic

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.scarlettjoubert.noteswithcomments.domain.entity.Note
import com.scarlettjoubert.noteswithcomments.domain.usecases.DeleteNoteUseCase
import com.scarlettjoubert.noteswithcomments.domain.usecases.GetNotesFromTopicUseCase
import com.scarlettjoubert.noteswithcomments.domain.usecases.SaveNoteUseCase
import com.scarlettjoubert.noteswithcomments.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class NotesFromTopicViewModel @Inject constructor(
    private val getNotesFromTopicUseCase: GetNotesFromTopicUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val saveNoteUseCase: SaveNoteUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val topic: String = checkNotNull(savedStateHandle[Screen.KEY_TOPIC])

    val screenState = getNotesFromTopicUseCase(topic)
        .map { list ->
        NotesFromTopicScreenState.NotesFromTopic(
            notes = list
        )
    }

    fun delete(note:Note){
        deleteNoteUseCase(note)
    }

    fun saveNote(note: Note){
        saveNoteUseCase(note)
    }
}


