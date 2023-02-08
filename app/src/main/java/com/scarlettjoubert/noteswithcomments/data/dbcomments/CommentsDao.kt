package com.scarlettjoubert.noteswithcomments.data.dbcomments

import androidx.room.*
import com.scarlettjoubert.noteswithcomments.data.dbnotes.Notes
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentsDao {

    @Query("SELECT * FROM comments WHERE toNote = :id ORDER BY created DESC")
    fun getAllComments(id:Int): Flow<List<Comments>>

    @Query("SELECT * FROM comments WHERE toNote = :id ORDER BY created DESC")
    fun getAllCommentsList(id:Int): List<Comments>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(text: Comments)

    @Update
    fun updateComment(text: Comments)

    @Query("DELETE FROM comments")
    suspend fun deleteAll()

    @Query("DELETE FROM comments WHERE id = :id")
    fun deleteComment(id:Int)
}
