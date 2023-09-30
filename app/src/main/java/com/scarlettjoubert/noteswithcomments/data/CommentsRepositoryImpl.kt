package com.scarlettjoubert.noteswithcomments.data

import androidx.annotation.WorkerThread
import com.scarlettjoubert.noteswithcomments.data.dbcomments.CommentsDto
import com.scarlettjoubert.noteswithcomments.data.dbcomments.CommentsDao
import com.scarlettjoubert.noteswithcomments.data.mapper.NotesMapper
import com.scarlettjoubert.noteswithcomments.domain.entity.Comment
import com.scarlettjoubert.noteswithcomments.domain.entity.Note
import com.scarlettjoubert.noteswithcomments.domain.repository.CommentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject

class CommentsRepositoryImpl @Inject constructor(
    private val commentsDao: CommentsDao,
    private val mapper: NotesMapper,
) : CommentRepository {

    private val repositoryScope = CoroutineScope(Dispatchers.Default)


    override fun getListOfCommentsFlow(toNote: Int): Flow<List<Comment>> {
        return commentsDao.getAllComments(toNote).map { list ->
            list.map { comment ->
                mapper.mapCommentDtoToComment(comment)
            }
        }
    }

    override fun updateComment(comment: Comment) {
        commentsDao.updateComment(
            text = comment.text,
            id = comment.id
        )
    }

    override fun createComment(toNote: Int, text: String) {
        commentsDao.insert(
            CommentsDto(
                id = null,
                toNote = toNote,
                text = text,
                created = Calendar.getInstance().time.time
            )
        )
    }

    override fun deleteComment(comment: Comment) {
        commentsDao.deleteComment(comment.id)
    }

    override fun saveComment(comment: Comment) {
        commentsDao.insert(mapper.mapCommentToCommentDto(comment))
    }

    override fun getAllCommentsList(toNote: Int): List<Comment> {
        return commentsDao.getAllCommentsList(toNote).map {
            mapper.mapCommentDtoToComment(it)
        }
    }

    override fun deleteAllCommentsForNote(note: Note) {
        commentsDao.deleteAllCommentsForNote(note.id ?: throw IllegalArgumentException())
    }

    fun getComments(id: Int): Flow<List<CommentsDto>> {
        return commentsDao.getAllComments(id)
    }


    fun getCommentsList(id: Int): List<CommentsDto> {
        return commentsDao.getAllCommentsList(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(comment: CommentsDto) {
        commentsDao.insert(comment)
    }

    suspend fun update(comment: CommentsDto) {
        commentsDao.updateComment(
            text = comment.text,
            id = comment.id!!
        )
    }

    fun delete(id: Int) {
        commentsDao.deleteComment(id)
    }
}