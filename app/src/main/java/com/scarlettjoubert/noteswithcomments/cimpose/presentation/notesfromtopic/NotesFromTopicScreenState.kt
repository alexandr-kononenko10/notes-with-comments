package com.scarlettjoubert.noteswithcomments.cimpose.presentation.notesfromtopic

import com.scarlettjoubert.noteswithcomments.domain.entity.Note

sealed class NotesFromTopicScreenState {

    object Initial: NotesFromTopicScreenState()

    data class AllTopics(
        val notes: List<Note>,
        val isSortInLine: Boolean = true
    ): NotesFromTopicScreenState()

    data class NotesFromTopic(
        val notes: List<Note>,
        val isSortInLine: Boolean = true
    ): NotesFromTopicScreenState()

}