package com.scarlettjoubert.noteswithcomments.ui.notes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.scarlettjoubert.noteswithcomments.DATEFORMAT
import com.scarlettjoubert.noteswithcomments.data.dbnotes.Notes
import com.scarlettjoubert.noteswithcomments.data.CommentsRepository
import com.scarlettjoubert.noteswithcomments.databinding.NoteItemBinding
import java.text.SimpleDateFormat
import java.util.*

class NotesAdapter (
    private val onClick: (Notes) -> Unit,
    private val delete: (Notes) -> Unit,
    private val commentsRepository: CommentsRepository
): ListAdapter<Notes, NoteViewHolder>(DiffUtilItemCallbackNotes()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context))
        return NoteViewHolder(binding)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = getItem(position)
        val longTime = item?.created!!
        val date = Date(longTime)
        val format = SimpleDateFormat(DATEFORMAT)

        with(holder.binding){
            textViewNoteTopic.text = item.topic
            textViewNote.text = item.text
            textViewNoteCreated.text = format.format(date)
            textViewCommentsCounter.text = commentsRepository.getCommentsList(item.id!!).size.toString()
            cardViewItem.setOnClickListener{
              item?.let { onClick(item) }
            }
            imageViewDeleteNote.setOnClickListener {
                item?.let { delete(item) }
            }
        }
    }
}

class DiffUtilItemCallbackNotes : DiffUtil.ItemCallback<Notes>() {
    override fun areItemsTheSame(oldItem: Notes, newItem: Notes): Boolean =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Notes, newItem: Notes): Boolean =
        oldItem == newItem
}