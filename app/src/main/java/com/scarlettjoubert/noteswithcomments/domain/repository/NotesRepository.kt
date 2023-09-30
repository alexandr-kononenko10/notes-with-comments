package com.scarlettjoubert.noteswithcomments.domain.repository

import com.scarlettjoubert.noteswithcomments.domain.entity.Note
import com.scarlettjoubert.noteswithcomments.domain.entity.Topic
import kotlinx.coroutines.flow.StateFlow

interface NotesRepositoryInterface {

 fun getListOfNotesDesc(): StateFlow<List<Note>>

 fun getAllNotesList():List<Note>

 fun getListOfNotesFromTopic(topic:String): StateFlow<List<Note>>

 fun getListOfTopics(): StateFlow<List<Topic>>

 fun createNote(text:String, topic:String)

 fun updateNote(note: Note)

 fun deleteNote(note:Note)

 fun searchNote(query:String):StateFlow<List<Note>>

 fun saveNte(note: Note)
}