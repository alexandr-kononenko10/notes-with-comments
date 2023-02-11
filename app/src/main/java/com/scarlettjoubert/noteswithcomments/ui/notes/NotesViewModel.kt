package com.scarlettjoubert.noteswithcomments.ui.notes

import androidx.lifecycle.ViewModel
import com.scarlettjoubert.noteswithcomments.data.NotesRepository
import com.scarlettjoubert.noteswithcomments.data.CommentsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val repository:NotesRepository, val commentsRepository: CommentsRepository) : ViewModel() {

    val noteFlow = repository.notes

    fun delete(id:Int){
        repository.delete(id)
    }

}