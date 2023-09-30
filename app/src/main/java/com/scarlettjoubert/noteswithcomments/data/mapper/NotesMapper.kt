package com.scarlettjoubert.noteswithcomments.data.mapper

import android.util.Log
import com.scarlettjoubert.noteswithcomments.data.dbcomments.CommentsDto
import com.scarlettjoubert.noteswithcomments.data.dbnotes.NotesDto
import com.scarlettjoubert.noteswithcomments.domain.entity.Comment
import com.scarlettjoubert.noteswithcomments.domain.entity.Note
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class NotesMapper @Inject constructor() {

    fun mapNotesDtoToNote(notesDto: NotesDto): Note {
        return Note(
            id = notesDto.id ?: throw IllegalArgumentException(),
            text = notesDto.text,
            topic = notesDto.topic,
            created = notesDto.created.timeToString(),
            createdLong = notesDto.created
        )
    }


    fun mapListOfNotesDtoToNote(list: List<NotesDto>): List<Note> {
        return list.map {
            mapNotesDtoToNote(it)
        }
    }

    fun mapNoteToToNoteDto(note:Note):NotesDto{
        return NotesDto(
            id = note.id,
            topic = note.topic,
            text = note.text,
            created = note.createdLong
        )
    }

    fun mapCommentDtoToComment(commentsDto: CommentsDto):Comment{
        return Comment(
            id = commentsDto.id ?: throw IllegalArgumentException(),
            text = commentsDto.text,
            toNote = commentsDto.toNote,
            created = commentsDto.created.timeToString(),
            createdLong = commentsDto.created
        )
    }

    fun mapListOfCommentDtoToComments( list:List<CommentsDto>): List<Comment>{
        return list.map {
            mapCommentDtoToComment(it)
        }
    }

    fun mapCommentToCommentDto(comment: Comment):CommentsDto{
        return CommentsDto(
            id = comment.id,
            toNote = comment.toNote,
            text = comment.text,
            created = comment.createdLong
        )
    }
}



private fun Long.timeToString(): String {
    val date = Date(this)
    return SimpleDateFormat("d MMMM yyyy, hh:mm", Locale.getDefault()).format(date)
}
