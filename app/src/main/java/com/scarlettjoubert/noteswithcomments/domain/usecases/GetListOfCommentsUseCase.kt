package com.scarlettjoubert.noteswithcomments.domain.usecases

import com.scarlettjoubert.noteswithcomments.domain.entity.Comment
import com.scarlettjoubert.noteswithcomments.domain.repository.CommentRepository
import javax.inject.Inject

class GetListOfCommentsUseCase @Inject constructor(
    private val commentRepository: CommentRepository
) {
    operator fun invoke(toNote:Int): List<Comment>{
       return commentRepository.getAllCommentsList(toNote)
    }
}