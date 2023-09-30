package com.scarlettjoubert.noteswithcomments.ui.notesfromtopic

import androidx.lifecycle.ViewModel
import com.scarlettjoubert.noteswithcomments.data.CommentsRepositoryImpl
import com.scarlettjoubert.noteswithcomments.data.NotesRepository
import com.scarlettjoubert.noteswithcomments.data.dbnotes.NotesDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NotesFromTopicViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
    val commentsRepositoryImpl: CommentsRepositoryImpl
) : ViewModel() {

    fun getNotes(topic: String): Flow<List<NotesDto>> = notesRepository.getFlowNotesFromTopic(topic)

    fun delete(id: Int) {
        notesRepository.delete(id)
    }
}