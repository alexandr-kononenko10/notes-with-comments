package com.scarlettjoubert.noteswithcomments.data.dbnotes

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class NotesRoomDataBase: RoomDatabase() {
    abstract fun notesDao(): NotesDao
}