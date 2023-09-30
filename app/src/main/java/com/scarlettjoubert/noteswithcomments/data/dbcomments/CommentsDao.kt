package com.scarlettjoubert.noteswithcomments.data.dbcomments

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentsDao {

    @Query("SELECT * FROM comments WHERE toNote = :id ORDER BY created DESC")
    fun getAllComments(id:Int): Flow<List<CommentsDto>>

    @Query("SELECT * FROM comments WHERE toNote = :id ORDER BY created DESC")
   fun getAllCommentsList(id:Int): List<CommentsDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(text: CommentsDto)

//    @Update
//    suspend fun updateComment(text: CommentsDto)
    @Query("UPDATE comments SET text= :text WHERE id = :id")
    fun updateComment(text: String, id:Int)

    @Query("DELETE FROM comments")
    suspend fun deleteAll()

    @Query("DELETE FROM comments WHERE id = :id")
    fun deleteComment(id:Int)

    @Query("DELETE FROM comments WHERE toNote = :toNote")
    fun deleteAllCommentsForNote(toNote:Int)
}
