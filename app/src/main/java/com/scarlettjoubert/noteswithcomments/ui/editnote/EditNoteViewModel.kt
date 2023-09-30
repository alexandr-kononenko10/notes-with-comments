package com.scarlettjoubert.noteswithcomments.ui.editnote

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.scarlettjoubert.noteswithcomments.R
import com.scarlettjoubert.noteswithcomments.data.CommentsRepositoryImpl
import com.scarlettjoubert.noteswithcomments.data.NotesRepository
import com.scarlettjoubert.noteswithcomments.data.dbcomments.CommentsDto
import com.scarlettjoubert.noteswithcomments.data.dbnotes.NotesDto

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val repository: NotesRepository,
    private val commentsRepositoryImpl: CommentsRepositoryImpl
) : ViewModel() {

    fun getComments(id: Int): Flow<List<CommentsDto>> {
        return commentsRepositoryImpl.getComments(id)
    }

    suspend fun insertComment(id: Int, text: String) {
        val time = Calendar.getInstance().time.time
        val newComment = CommentsDto(null, id, text, time)
        commentsRepositoryImpl.insert(newComment)
    }

    fun update(id: Int, topic: String, text: String, created: Long) {
        val updatedNote = NotesDto(id, text, topic, created)
        repository.update(updatedNote)
    }

    fun deleteComment(id: Int) {
        commentsRepositoryImpl.delete(id)
    }

    fun shareNote(id: Int, context: Context, topic: String, text: String) {
        val textMessage = makeText(id, context, topic, text)
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, textMessage)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        context.startActivity(shareIntent)
    }

    private fun makeText(id: Int, context: Context, topic: String, text: String): String {
        var textMessage: String = ""
        val comments = commentsRepositoryImpl.getCommentsList(id)
        if (comments.isNotEmpty()) {
            val commentsText = mutableListOf<String>()
            comments.forEach { commentsText.add(it.text) }
            textMessage =
                context.getString(R.string.share_topic) + " " + topic + "\n" + text + "\n" +
                        context.getString(R.string.separator) + "\n" +
            context.getString(R.string.share_comments) + "\n" + commentsText.joinToString(
                separator = "\n"
            )
        } else {
            textMessage = context.getString(R.string.share_topic) + " " + topic + "\n" + text + "\n"
        }
        return textMessage
    }
}