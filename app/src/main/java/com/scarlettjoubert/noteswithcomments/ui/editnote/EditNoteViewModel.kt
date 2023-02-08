package com.scarlettjoubert.noteswithcomments.ui.editnote

import androidx.lifecycle.ViewModel
import com.scarlettjoubert.noteswithcomments.data.NotesRepository
import com.scarlettjoubert.noteswithcomments.data.dbcomments.Comments
import com.scarlettjoubert.noteswithcomments.data.dbnotes.Notes
import com.scarlettjoubert.noteswithcomments.data.model.CommentsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(private val repository:NotesRepository,
private val commentsRepository: CommentsRepository) : ViewModel() {

    fun getComments(id:Int):Flow<List<Comments>>{
        return commentsRepository.getComments(id)
    }
   suspend fun insertComment(id:Int, text:String){
       val time = Calendar.getInstance().time.time
       val newComment = Comments(null, id, text, time )
        commentsRepository.insert(newComment)
    }

    fun update(id:Int, topic:String, text:String, created:Long ){
        val updatedNote = Notes(id, text, topic, created)
        repository.update(updatedNote)
    }
    fun deleteComment(id:Int){
        commentsRepository.delete(id)
    }
}