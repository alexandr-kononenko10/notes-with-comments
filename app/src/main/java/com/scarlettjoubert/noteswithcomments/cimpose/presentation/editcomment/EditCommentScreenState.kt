package com.scarlettjoubert.noteswithcomments.cimpose.presentation.editcomment

import com.scarlettjoubert.noteswithcomments.domain.entity.Comment
import java.util.IdentityHashMap

sealed class EditCommentScreenState{

    object Initial: EditCommentScreenState()

    class Editing(
        val comment: Comment,
    ): EditCommentScreenState()

    class Creating(
        val comment: Comment
    ): EditCommentScreenState()

}