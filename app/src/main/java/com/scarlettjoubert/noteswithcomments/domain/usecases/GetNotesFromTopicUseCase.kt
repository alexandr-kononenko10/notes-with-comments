package com.scarlettjoubert.noteswithcomments.domain.usecases

import com.scarlettjoubert.noteswithcomments.domain.entity.Note
import com.scarlettjoubert.noteswithcomments.domain.repository.NotesRepositoryInterface
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetNotesFromTopicUseCase @Inject constructor(
    private val repository: NotesRepositoryInterface,
) {
    operator fun invoke(topic: String): StateFlow<List<Note>> {
        return repository.getListOfNotesFromTopic(topic)
    }
}