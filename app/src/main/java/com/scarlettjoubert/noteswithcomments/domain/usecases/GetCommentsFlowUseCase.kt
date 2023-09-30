package com.scarlettjoubert.noteswithcomments.domain.usecases

import com.scarlettjoubert.noteswithcomments.domain.entity.Comment
import com.scarlettjoubert.noteswithcomments.domain.entity.Note
import com.scarlettjoubert.noteswithcomments.domain.repository.CommentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCommentsFlowUseCase @Inject constructor(
    private val commentRepository: CommentRepository,
) {
    operator fun invoke(noteId:Int?): Flow<List<Comment>> {
      return commentRepository.getListOfCommentsFlow(noteId ?: throw IllegalArgumentException())
    }
}