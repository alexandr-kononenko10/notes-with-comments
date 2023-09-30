package com.scarlettjoubert.noteswithcomments.cimpose.presentation.topics

import com.scarlettjoubert.noteswithcomments.cimpose.presentation.notes.NotesScreenState
import com.scarlettjoubert.noteswithcomments.domain.entity.Note
import com.scarlettjoubert.noteswithcomments.domain.entity.Topic

sealed class TopicsScreenState{

object Initial: TopicsScreenState()

data class Topics(
    val topics: List<Topic>,
    val isSortInLine: Boolean = true
): TopicsScreenState()

}
