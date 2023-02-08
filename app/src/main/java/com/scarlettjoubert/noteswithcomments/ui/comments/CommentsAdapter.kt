package com.scarlettjoubert.noteswithcomments.ui.comments

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.scarlettjoubert.noteswithcomments.data.dbcomments.Comments
import com.scarlettjoubert.noteswithcomments.data.dbnotes.Notes
import com.scarlettjoubert.noteswithcomments.data.model.Note
import com.scarlettjoubert.noteswithcomments.databinding.NoteItemBinding
import com.scarlettjoubert.noteswithcomments.ui.notes.NoteViewHolder

class CommentsAdapter(
    private val onClick: (Comments) -> Unit,
    private val deleteComment: (Comments) -> Unit
): ListAdapter<Comments, NoteViewHolder>(DiffUtilItemCallbackNotes()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context))
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.binding){
            textViewNoteTopic.isVisible = false
            imageViewCommentsCounter.isVisible = false
            textViewCommentsCounter.isVisible = false
            textViewNote.text = item.text
            textViewNoteCreated.text = item.created.toString()
            cardViewItem.setOnClickListener{
                Log.i("click", "click")
              item?.let { onClick(item) }
            }
            imageViewDeleteNote.setOnClickListener {
                item?.let { deleteComment(item) }
            }
        }
    }
}

class DiffUtilItemCallbackNotes : DiffUtil.ItemCallback<Comments>() {
    override fun areItemsTheSame(oldItem: Comments, newItem: Comments): Boolean =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Comments, newItem: Comments): Boolean =
        oldItem == newItem
}