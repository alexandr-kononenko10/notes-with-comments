package com.scarlettjoubert.noteswithcomments.domain.entity

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Note(
    val id: Int?,
    val text: String,
    val topic: String,
    val created: String,
    val createdLong: Long,
): SavingItem