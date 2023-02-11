package com.scarlettjoubert.noteswithcomments.ui.notesfromtopic

import androidx.lifecycle.ViewModel
import com.scarlettjoubert.noteswithcomments.data.NotesRepository
import com.scarlettjoubert.noteswithcomments.data.dbnotes.Notes
import com.scarlettjoubert.noteswithcomments.data.CommentsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NotesFromTopicViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
    val commentsRepository: CommentsRepository
) : ViewModel() {

    fun getNotes(topic: String): Flow<List<Notes>> = notesRepository.getFlowNotesFromTopic(topic)

    fun delete(id: Int) {
        notesRepository.delete(id)
    }
}