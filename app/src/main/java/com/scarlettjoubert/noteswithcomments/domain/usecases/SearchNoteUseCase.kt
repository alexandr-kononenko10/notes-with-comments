package com.scarlettjoubert.noteswithcomments.domain.usecases

import com.scarlettjoubert.noteswithcomments.domain.entity.Note
import com.scarlettjoubert.noteswithcomments.domain.repository.NotesRepositoryInterface
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SearchNoteUseCase @Inject constructor(
    private val repository: NotesRepositoryInterface
) {
    operator fun invoke(query:String): StateFlow<List<Note>> {
        return repository.searchNote(query)
    }
}