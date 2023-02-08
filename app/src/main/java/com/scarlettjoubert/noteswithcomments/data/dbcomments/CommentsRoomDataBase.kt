package com.scarlettjoubert.noteswithcomments.data.dbcomments

import androidx.room.Database
import androidx.room.RoomDatabase
import com.scarlettjoubert.noteswithcomments.data.dbnotes.Notes
import com.scarlettjoubert.noteswithcomments.data.dbnotes.NotesDao

@Database(entities = [Comments::class], version = 1, exportSchema = false)
abstract class CommentsRoomDataBase: RoomDatabase() {
    abstract fun commentsDao(): CommentsDao
}