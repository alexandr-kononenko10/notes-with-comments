package com.scarlettjoubert.noteswithcomments.data.dbnotes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Notes(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "topic") val topic: String,
    @ColumnInfo(name = "created") val created: Long,
)