package com.scarlettjoubert.noteswithcomments.domain.usecases

import com.scarlettjoubert.noteswithcomments.domain.entity.Comment
import com.scarlettjoubert.noteswithcomments.domain.entity.SavingStatus
import com.scarlettjoubert.noteswithcomments.domain.repository.CommentRepository
import javax.inject.Inject

class SaveCommentUseCase @Inject constructor(
    private val commentRepository: CommentRepository,
) {
    operator fun invoke(comment: Comment) {
        try {
            commentRepository.saveComment(comment)
        } catch (_: Exception) {
        }
    }
}