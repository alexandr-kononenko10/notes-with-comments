package com.scarlettjoubert.noteswithcomments.domain.usecases

import com.scarlettjoubert.noteswithcomments.domain.entity.Comment
import com.scarlettjoubert.noteswithcomments.domain.entity.SavingStatus
import com.scarlettjoubert.noteswithcomments.domain.repository.CommentRepository
import java.lang.Exception
import javax.inject.Inject

class UpdateCommentUseCase @Inject constructor(
    private val commentRepository: CommentRepository,
) {
    operator fun invoke(comment: Comment): SavingStatus {
        return try {
            commentRepository.updateComment(
                comment = comment
            )
            SavingStatus.Success
        } catch (e: Exception) {
            SavingStatus.UnknownError
        }
    }
}