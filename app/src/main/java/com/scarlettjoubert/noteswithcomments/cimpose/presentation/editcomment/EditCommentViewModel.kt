package com.scarlettjoubert.noteswithcomments.cimpose.presentation.editcomment

import android.content.Context
import android.content.Intent
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.scarlettjoubert.noteswithcomments.R
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.editnote.EditNoteScreenState
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.editnote.EditNoteViewModel
import com.scarlettjoubert.noteswithcomments.domain.entity.Comment
import com.scarlettjoubert.noteswithcomments.domain.entity.SavingStatus
import com.scarlettjoubert.noteswithcomments.domain.usecases.CreateCommentUseCase
import com.scarlettjoubert.noteswithcomments.domain.usecases.DeleteCommentUseCase
import com.scarlettjoubert.noteswithcomments.domain.usecases.GetListOfCommentsUseCase
import com.scarlettjoubert.noteswithcomments.domain.usecases.UpdateCommentUseCase
import com.scarlettjoubert.noteswithcomments.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EditCommentViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val updateCommentUseCase: UpdateCommentUseCase,
    private val createCommentUseCase: CreateCommentUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase,
    private val getListOfCommentUseCase: GetListOfCommentsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<EditCommentScreenState>(EditCommentScreenState.Initial)
    val state = _state.asStateFlow()


    init {
        checkState()
    }

    private fun checkState() {
        val commentJson: String? = savedStateHandle[Screen.KEY_EDIT_COMMENT]
        if (commentJson !== null) {
            val comment = Gson().fromJson(commentJson, Comment::class.java)
            _state.value = EditCommentScreenState.Editing(comment)
        } else {
            val noteId: Int = checkNotNull(savedStateHandle[Screen.KEY_NOTE_ID])
            createCommentUseCase(toNote = noteId)
            val newComment = getListOfCommentUseCase(noteId).first()
            _state.value = EditCommentScreenState.Creating(newComment)
        }
    }

    fun saveComment(text: String): SavingStatus {
        return when (val currentState = state.value) {
            is EditCommentScreenState.Editing -> {
                val newComment = currentState.comment.copy(text = text)
                _state.value = EditCommentScreenState.Editing(newComment)
                updateCommentUseCase(newComment)
            }

            is EditCommentScreenState.Creating -> {
                val newComment = currentState.comment.copy(text = text)
                _state.value = EditCommentScreenState.Creating(newComment)
                updateCommentUseCase(newComment)
            }

            else -> SavingStatus.UnknownError
        }
    }

    fun deleteComment(): SavingStatus {
        return when (val currentState = state.value) {

            is EditCommentScreenState.Editing -> {
                deleteCommentUseCase(currentState.comment)
            }

            is EditCommentScreenState.Creating -> {
                deleteCommentUseCase(currentState.comment)
            }

            else -> SavingStatus.UnknownError
        }
    }

    fun onBackPressed(onBackPressedAction: () -> Unit): SavingStatus {
        return when (val currentState = state.value) {
            is EditCommentScreenState.Creating -> {
                return if (currentState.comment.text.isEmpty()) {
                    deleteComment()
                    onBackPressedAction()
                    SavingStatus.ErrorEmptyElement
                } else {
                    onBackPressedAction()
                    SavingStatus.Success
                }
            }

            else -> {
                onBackPressedAction()
                SavingStatus.Success
            }
        }
    }

    fun shareComment(context: Context) {
        when (val currentState = state.value) {
            is EditCommentScreenState.Editing -> {
                val textMessage = currentState.comment.text
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, textMessage)
                    type = EditNoteViewModel.typeText
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                context.startActivity(shareIntent)
            }

            is EditCommentScreenState.Creating -> {
                val textMessage = currentState.comment.text
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, textMessage)
                    type = EditNoteViewModel.typeText
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                context.startActivity(shareIntent)
            }

            EditCommentScreenState.Initial -> {}
        }

    }

}