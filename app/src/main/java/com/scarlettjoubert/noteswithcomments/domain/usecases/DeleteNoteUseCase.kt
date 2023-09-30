package com.scarlettjoubert.noteswithcomments.domain.usecases

import com.scarlettjoubert.noteswithcomments.domain.entity.Note
import com.scarlettjoubert.noteswithcomments.domain.entity.SavingStatus
import com.scarlettjoubert.noteswithcomments.domain.repository.CommentRepository
import com.scarlettjoubert.noteswithcomments.domain.repository.NotesRepositoryInterface
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: NotesRepositoryInterface,
    private val commentRepository: CommentRepository
) {
    operator fun invoke(note: Note): SavingStatus {
        return try {
            repository.deleteNote(note)
            commentRepository.deleteAllCommentsForNote(note)
            SavingStatus.DeletedItem(note)
        }
        catch (e:Exception){
            SavingStatus.UnknownError
        }
    }
}
