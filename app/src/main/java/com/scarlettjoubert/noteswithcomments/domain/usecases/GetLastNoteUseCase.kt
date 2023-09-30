package com.scarlettjoubert.noteswithcomments.domain.usecases

import com.scarlettjoubert.noteswithcomments.domain.entity.Note
import com.scarlettjoubert.noteswithcomments.domain.repository.NotesRepositoryInterface
import javax.inject.Inject

class GetLastNoteUseCase @Inject constructor(
    private val repository: NotesRepositoryInterface
) {
    operator fun invoke(): Note {
        return repository.getAllNotesList().first()
    }
}