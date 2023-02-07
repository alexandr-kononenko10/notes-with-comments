package com.scarlettjoubert.noteswithcomments.data

import androidx.annotation.WorkerThread
import com.scarlettjoubert.noteswithcomments.data.dbnotes.Notes
import com.scarlettjoubert.noteswithcomments.data.dbnotes.NotesDao
import com.scarlettjoubert.noteswithcomments.data.model.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesRepository @Inject constructor(private val notesDao: NotesDao) {

    val notes: Flow<List<Notes>> = notesDao.getAllNotesAsc()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(note: Notes) {
        notesDao.insert(note)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll(){
        notesDao.deleteAll()
    }
    fun update(note:Notes){
        notesDao.updateNote(note)
    }
    //добавить все функции!!!
}