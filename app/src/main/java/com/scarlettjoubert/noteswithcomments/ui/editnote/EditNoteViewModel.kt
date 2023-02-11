package com.scarlettjoubert.noteswithcomments.ui.editnote

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.room.util.joinIntoString
import com.scarlettjoubert.noteswithcomments.R
import com.scarlettjoubert.noteswithcomments.data.NotesRepository
import com.scarlettjoubert.noteswithcomments.data.dbcomments.Comments
import com.scarlettjoubert.noteswithcomments.data.dbnotes.Notes
import com.scarlettjoubert.noteswithcomments.data.CommentsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.forEach
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val repository: NotesRepository,
    private val commentsRepository: CommentsRepository
) : ViewModel() {

    fun getComments(id: Int): Flow<List<Comments>> {
        return commentsRepository.getComments(id)
    }

    suspend fun insertComment(id: Int, text: String) {
        val time = Calendar.getInstance().time.time
        val newComment = Comments(null, id, text, time)
        commentsRepository.insert(newComment)
    }

    fun update(id: Int, topic: String, text: String, created: Long) {
        val updatedNote = Notes(id, text, topic, created)
        repository.update(updatedNote)
    }

    fun deleteComment(id: Int) {
        commentsRepository.delete(id)
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
        val comments = commentsRepository.getCommentsList(id)
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