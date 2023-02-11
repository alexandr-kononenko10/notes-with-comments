package com.scarlettjoubert.noteswithcomments.data

import androidx.annotation.WorkerThread
import com.scarlettjoubert.noteswithcomments.data.dbcomments.Comments
import com.scarlettjoubert.noteswithcomments.data.dbcomments.CommentsDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommentsRepository @Inject constructor(private val commentsDao: CommentsDao) {

    fun getComments(id: Int): Flow<List<Comments>> {
       return commentsDao.getAllComments(id)
    }

    fun getCommentsList(id: Int): List<Comments> {
        return commentsDao.getAllCommentsList(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(comment: Comments) {
        commentsDao.insert(comment)
    }

    fun update(comment: Comments){
        commentsDao.updateComment(comment)
    }

    fun delete(id:Int){
        commentsDao.deleteComment(id)
    }
}