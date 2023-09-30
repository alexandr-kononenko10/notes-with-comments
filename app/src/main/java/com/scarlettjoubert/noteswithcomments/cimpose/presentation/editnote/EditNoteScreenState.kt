package com.scarlettjoubert.noteswithcomments.cimpose.presentation.editnote

import com.scarlettjoubert.noteswithcomments.domain.entity.Comment
import com.scarlettjoubert.noteswithcomments.domain.entity.Note

sealed class EditNoteScreenState {

    object Initial : EditNoteScreenState()

    class Editing(
        val note: Note,
        val lastComment: Comment?,
        val commentsCount: Int,
    ) : EditNoteScreenState()

    class Creating(
       val note:Note
    ):EditNoteScreenState()
}
