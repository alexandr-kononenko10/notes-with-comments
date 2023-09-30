package com.scarlettjoubert.noteswithcomments.domain.repository

import com.scarlettjoubert.noteswithcomments.domain.entity.Comment
import com.scarlettjoubert.noteswithcomments.domain.entity.Note
import kotlinx.coroutines.flow.Flow

interface CommentRepository {

    fun getListOfCommentsFlow(toNote:Int): Flow<List<Comment>>

    fun updateComment(
        comment: Comment
    )

     fun createComment(
        toNote:Int,
        text:String
    )

     fun saveComment(comment: Comment)

    fun deleteComment(comment: Comment)

    fun getAllCommentsList(toNote: Int):List<Comment>

    fun deleteAllCommentsForNote(note: Note)

}