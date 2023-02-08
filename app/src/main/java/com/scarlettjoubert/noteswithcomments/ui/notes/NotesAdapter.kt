package com.scarlettjoubert.noteswithcomments.ui.notes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.scarlettjoubert.noteswithcomments.data.dbnotes.Notes
import com.scarlettjoubert.noteswithcomments.data.model.CommentsRepository
import com.scarlettjoubert.noteswithcomments.data.model.Note
import com.scarlettjoubert.noteswithcomments.databinding.NoteItemBinding
import javax.inject.Inject

class NotesAdapter (
    private val onClick: (Notes) -> Unit,
    private val delete: (Notes) -> Unit,
    private val commentsRepository: CommentsRepository
): ListAdapter<Notes, NoteViewHolder>(DiffUtilItemCallbackNotes()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context))
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.binding){
            textViewNoteTopic.text = item.topic
            textViewNote.text = item.text
            textViewNoteCreated.text = item.created.toString()
            textViewCommentsCounter.text = commentsRepository.getCommentsCount(item.id!!).toString()
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