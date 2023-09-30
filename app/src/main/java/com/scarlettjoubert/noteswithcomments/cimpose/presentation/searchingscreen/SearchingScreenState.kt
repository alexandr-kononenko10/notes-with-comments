package com.scarlettjoubert.noteswithcomments.cimpose.presentation.searchingscreen

import com.scarlettjoubert.noteswithcomments.domain.entity.Note

sealed class SearchingScreenState{

    object Initial:SearchingScreenState()

    class NoteSearching(
        val query:String,
        val results:List<Note>
    ):SearchingScreenState()

    object NoResults:SearchingScreenState()
}
