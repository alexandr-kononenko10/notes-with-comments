package com.scarlettjoubert.noteswithcomments.data.dbcomments

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CommentsDto::class], version = 1, exportSchema = false)
abstract class CommentsRoomDataBase: RoomDatabase() {
    abstract fun commentsDao(): CommentsDao
}