package com.scarlettjoubert.noteswithcomments.data.dbnotes

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes ORDER BY created ASC")
    fun getAllNotesAsc(): Flow<List<Notes>>

    @Query("SELECT * FROM notes WHERE id = :id ORDER BY created ASC")
    fun getNoteByID(id:Int): List<Notes>

    @Query("SELECT TRIM (topic) FROM notes ORDER BY created ASC")
    fun getTopics(): Flow<List<String>>

    @Query("SELECT * FROM notes WHERE TRIM (topic) = :topic ORDER BY created ASC")
    fun geNotesFromTopicAsc(topic:String): List<Notes>

    @Query("SELECT * FROM notes WHERE TRIM (topic) = :topic ORDER BY created ASC")
    fun geFlowNotesFromTopicAsc(topic:String): Flow<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(text: Notes)

    @Update
    fun updateNote(text:Notes)

    @Query("DELETE FROM notes")
    suspend fun deleteAll()

    @Query("DELETE FROM notes WHERE id = :id")
    fun deleteNotes(id:Int)

    @Query("SELECT * FROM notes WHERE text LIKE '%' || :query || '%' OR topic LIKE '%' || :query || '%' ORDER BY created ASC")
    fun search(query:String) : Flow<List<Notes>>
}