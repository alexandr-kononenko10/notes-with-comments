package com.scarlettjoubert.noteswithcomments.cimpose.presentation.comments

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scarlettjoubert.noteswithcomments.domain.entity.Comment
import com.scarlettjoubert.noteswithcomments.domain.repository.CommentRepository
import com.scarlettjoubert.noteswithcomments.domain.usecases.DeleteCommentUseCase
import com.scarlettjoubert.noteswithcomments.domain.usecases.GetCommentsFlowUseCase
import com.scarlettjoubert.noteswithcomments.domain.usecases.GetListOfCommentsUseCase
import com.scarlettjoubert.noteswithcomments.domain.usecases.SaveCommentUseCase
import com.scarlettjoubert.noteswithcomments.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CommentScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val deleteCommentUseCase: DeleteCommentUseCase,
    private val saveCommentUseCase: SaveCommentUseCase,
    private val getCommentsFlowUseCase: GetCommentsFlowUseCase
) : ViewModel() {

    val noteId: Int = checkNotNull(savedStateHandle[Screen.KEY_COMMENT])

    val state: StateFlow<CommentScreenState> = getCommentsFlowUseCase(noteId)
        .map {
            CommentScreenState.Comments(
                toNote = noteId,
                comments = it
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = CommentScreenState.Initial
        )

    fun saveComment(comment: Comment) = saveCommentUseCase(comment)

    fun delete(comment: Comment) = deleteCommentUseCase(comment)

}