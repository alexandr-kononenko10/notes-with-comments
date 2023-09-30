package com.scarlettjoubert.noteswithcomments.ui.searchnote

import androidx.lifecycle.ViewModel
import com.scarlettjoubert.noteswithcomments.data.CommentsRepositoryImpl
import com.scarlettjoubert.noteswithcomments.data.NotesRepository
import com.scarlettjoubert.noteswithcomments.data.dbnotes.NotesDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchNoteViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
    val commentsRepositoryImpl: CommentsRepositoryImpl
) : ViewModel() {

    fun search(query: String): Flow<List<NotesDto>> = notesRepository.search(query)

    fun delete(id: Int) = notesRepository.delete(id)

}