package com.scarlettjoubert.noteswithcomments.ui.searchnote

import androidx.lifecycle.ViewModel
import com.scarlettjoubert.noteswithcomments.data.NotesRepository
import com.scarlettjoubert.noteswithcomments.data.dbnotes.Notes
import com.scarlettjoubert.noteswithcomments.data.CommentsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchNoteViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
    val commentsRepository: CommentsRepository
) : ViewModel() {

    fun search(query: String): Flow<List<Notes>> = notesRepository.search(query)

    fun delete(id: Int) = notesRepository.delete(id)

}