package com.scarlettjoubert.noteswithcomments.cimpose.presentation.comments

import com.scarlettjoubert.noteswithcomments.domain.entity.Comment
import java.util.IdentityHashMap

sealed class CommentScreenState{

    object Initial: CommentScreenState()

    class Comments(
        val toNote:Int,
        val comments:List<Comment>
    ): CommentScreenState()

    class CreatingComment(
        val toNote:Int
    ): CommentScreenState()

}