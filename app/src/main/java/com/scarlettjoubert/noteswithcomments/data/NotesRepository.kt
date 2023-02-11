package com.scarlettjoubert.noteswithcomments.data

import androidx.annotation.WorkerThread
import com.scarlettjoubert.noteswithcomments.data.dbnotes.Notes
import com.scarlettjoubert.noteswithcomments.data.dbnotes.NotesDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesRepository @Inject constructor(private val notesDao: NotesDao) {

    val notes: Flow<List<Notes>> = notesDao.getAllNotesAsc()

    val topics: Flow<List<String>> = notesDao.getTopics()

    fun getNotesFromTopic(topic: String): List<Notes> = notesDao.geNotesFromTopicAsc(topic)

    fun getNoteById(id:Int): List<Notes> = notesDao.getNoteByID(id)

    fun getFlowNotesFromTopic(topic: String): Flow<List<Notes>> =
        notesDao.geFlowNotesFromTopicAsc(topic)

    fun insert(note: Notes) = notesDao.insert(note)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAll() {
        notesDao.deleteAll()
    }

    fun update(note: Notes) = notesDao.updateNote(note)

    fun delete(id: Int) = notesDao.deleteNotes(id)

    fun search(query:String): Flow<List<Notes>> = notesDao.search(query)
}