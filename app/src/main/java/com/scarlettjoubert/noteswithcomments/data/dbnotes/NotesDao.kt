package com.scarlettjoubert.noteswithcomments.data.dbnotes

import androidx.room.*
import com.scarlettjoubert.noteswithcomments.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes ORDER BY created ASC")
    fun getAllNotesAsc(): Flow<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(text: Notes)

    @Update
    fun updateNote(text:Notes)

    @Query("DELETE FROM notes")
    suspend fun deleteAll()

    @Delete
    fun deleteNotes(vararg note: Notes)
}