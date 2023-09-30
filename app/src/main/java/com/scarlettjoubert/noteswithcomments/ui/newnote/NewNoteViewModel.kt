package com.scarlettjoubert.noteswithcomments.ui.newnote

import androidx.lifecycle.ViewModel
import com.scarlettjoubert.noteswithcomments.data.NotesRepository
import com.scarlettjoubert.noteswithcomments.data.dbnotes.NotesDto
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class NewNoteViewModel @Inject constructor(private val notesRepository: NotesRepository) :
    ViewModel() {

   fun insert(topic:String, text:String){
            val time = Calendar.getInstance().time.time
            val newNote = NotesDto(null, text, topic, time)
            notesRepository.insert(newNote)
        }
}