package com.scarlettjoubert.noteswithcomments.cimpose.presentation.notes

import com.scarlettjoubert.noteswithcomments.domain.entity.Note

sealed class NotesScreenState {

    object Initial: NotesScreenState()

    data class NotesFromTopic(
        val topic:String,
        val notes: List<Note>,
        val isSortInLine: Boolean = true
    ): NotesScreenState()

    data class AllNotes(
        val notes: List<Note>,
        val isSortInLine: Boolean = true
    ): NotesScreenState()

}