package com.scarlettjoubert.noteswithcomments.domain.usecases

import com.scarlettjoubert.noteswithcomments.domain.entity.SavingStatus
import com.scarlettjoubert.noteswithcomments.domain.repository.CommentRepository
import javax.inject.Inject

class CreateCommentUseCase @Inject constructor(
    private val commentRepository: CommentRepository,
) {
    operator fun invoke(toNote: Int): SavingStatus {
        return try {
            commentRepository.createComment(
                toNote = toNote,
                text = ""
            )
            SavingStatus.Success
        } catch (e: Exception) {
            SavingStatus.UnknownError
        }
    }
}