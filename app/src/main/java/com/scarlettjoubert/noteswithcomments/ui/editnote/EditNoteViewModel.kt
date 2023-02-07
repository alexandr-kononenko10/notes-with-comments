package com.scarlettjoubert.noteswithcomments.ui.editnote

import androidx.lifecycle.ViewModel
import com.scarlettjoubert.noteswithcomments.data.NotesRepository
import com.scarlettjoubert.noteswithcomments.data.dbnotes.Notes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(private val repository:NotesRepository) : ViewModel() {

    fun update(id:Int, topic:String, text:String, created:Long ){
        val updatedNote = Notes(id, text, topic, created)
        repository.update(updatedNote)
    }
}