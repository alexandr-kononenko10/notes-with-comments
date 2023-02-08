package com.scarlettjoubert.noteswithcomments.data.dbcomments

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class Comments(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "toNote") val toNote: Int,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "created") val created: Long,
)