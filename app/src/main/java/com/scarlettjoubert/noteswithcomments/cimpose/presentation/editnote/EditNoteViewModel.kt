package com.scarlettjoubert.noteswithcomments.cimpose.presentation.editnote

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.scarlettjoubert.noteswithcomments.R
import com.scarlettjoubert.noteswithcomments.domain.entity.Comment
import com.scarlettjoubert.noteswithcomments.domain.entity.Note
import com.scarlettjoubert.noteswithcomments.domain.entity.SavingStatus
import com.scarlettjoubert.noteswithcomments.domain.usecases.CreateNoteUseCase
import com.scarlettjoubert.noteswithcomments.domain.usecases.DeleteNoteUseCase
import com.scarlettjoubert.noteswithcomments.domain.usecases.GetCommentsFlowUseCase
import com.scarlettjoubert.noteswithcomments.domain.usecases.GetListOfCommentsUseCase
import com.scarlettjoubert.noteswithcomments.domain.usecases.GetLastNoteUseCase
import com.scarlettjoubert.noteswithcomments.domain.usecases.UpdateNoteUseCase
import com.scarlettjoubert.noteswithcomments.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getCommentsFlowUseCase: GetCommentsFlowUseCase,
    private val createNoteUseCase: CreateNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val getLastNoteUseCase: GetLastNoteUseCase,
    private val getLastCommentUseCase: GetListOfCommentsUseCase,
    private val getListOfCommentsUseCase: GetListOfCommentsUseCase,
) : ViewModel() {

    private val _state =
        MutableStateFlow<EditNoteScreenState>(EditNoteScreenState.Initial as EditNoteScreenState)
    val state = _state.asStateFlow()

    private val commentsFlow = MutableStateFlow<Comment?>(null)

    init {
        checkState()
    }

    fun saveNote(text: String, topic: String): SavingStatus {
        return when (val currentState = state.value) {
            is EditNoteScreenState.Creating -> {
                val newNote = currentState.note.copy(text = text, topic = topic)
                _state.value = EditNoteScreenState.Creating(newNote)
                updateNote(newNote)
            }

            is EditNoteScreenState.Editing -> {
                val newNote = currentState.note.copy(text = text, topic = topic)
                updateNote(newNote)

                val lastCommentList =
                    getLastCommentUseCase(newNote.id ?: throw IllegalArgumentException())

                commentsFlow.value = lastCommentList.firstOrNull()

                _state.value = EditNoteScreenState.Editing(
                    note = newNote,
                    lastComment = commentsFlow.value,
                    commentsCount = lastCommentList.size
                )
                SavingStatus.Success
            }

            else -> SavingStatus.UnknownError
        }
    }

    fun onBackPressed(onBackPressedAction: () -> Unit): SavingStatus {
        return when (val currentState = state.value) {
            is EditNoteScreenState.Creating -> {
                return if (currentState.note.topic.isEmpty() && currentState.note.text.isEmpty()) {
                    deleteNote()
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

    private fun updateNote(newNote: Note): SavingStatus {
        return updateNoteUseCase(
            newNote
        )
    }

    fun deleteNote(): SavingStatus {
        return when (val currentState = state.value) {

            is EditNoteScreenState.Editing -> {
                deleteNoteUseCase(currentState.note)
            }

            is EditNoteScreenState.Creating -> {
                deleteNoteUseCase(currentState.note)
            }

            else -> SavingStatus.UnknownError
        }
    }

    private fun checkState() {
        val commentJson: String? = savedStateHandle[Screen.KEY_NOTE]

        if (commentJson == null) {
            createNoteUseCase()
            val newNote = getLastNoteUseCase()
            _state.value = EditNoteScreenState.Creating(note = newNote)
        } else {
            val note = Gson().fromJson(commentJson, Note::class.java)
            val commentsFlow = getCommentsFlowUseCase(note.id).stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = null
            )
            viewModelScope.launch {
                commentsFlow.collect {
                    _state.value = EditNoteScreenState.Editing(
                        note = note,
                        lastComment = commentsFlow.value?.firstOrNull(),
                        commentsCount = commentsFlow.value?.size ?: 0
                    )
                }
            }
        }
    }

    fun shareNote(context: Context) {
        when (val currentState = state.value) {
            is EditNoteScreenState.Editing -> {
                val textMessage = makeText(
                    id = currentState.note.id,
                    context = context,
                    topic = currentState.note.topic,
                    text = currentState.note.text
                )
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, textMessage)
                    type = typeText
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                context.startActivity(shareIntent)
            }

            is EditNoteScreenState.Creating -> {
                val textMessage = makeText(
                    id = currentState.note.id,
                    context = context,
                    topic = currentState.note.topic,
                    text = currentState.note.text
                )
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, textMessage)
                    type = typeText
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                context.startActivity(shareIntent)
            }

            EditNoteScreenState.Initial -> {}
        }

    }

    private fun makeText(id: Int?, context: Context, topic: String, text: String): String {
        var textMessage: String = emptyText
        val comments = if (id !== null)
            getListOfCommentsUseCase(id)
        else emptyList()
        textMessage = if (comments.isNotEmpty()) {
            val commentsText = mutableListOf<String>()
            comments.forEach { commentsText.add(it.text) }
            context.getString(R.string.share_topic) + " " + topic + "\n" + text + "\n" +
                    context.getString(R.string.separator) + "\n" +
                    context.getString(R.string.share_comments) + "\n" + commentsText.joinToString(
                separator = "\n"
            )
        } else {
            context.getString(R.string.share_topic) + " " + topic + "\n" + text + "\n"
        }
        return textMessage
    }

    companion object {
        val typeText = "text/plain"
        val emptyText = ""
    }
}