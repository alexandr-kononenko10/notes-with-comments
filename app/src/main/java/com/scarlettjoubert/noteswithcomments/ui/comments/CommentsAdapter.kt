package com.scarlettjoubert.noteswithcomments.ui.comments

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.scarlettjoubert.noteswithcomments.R
import com.scarlettjoubert.noteswithcomments.data.dbcomments.CommentsDto
import com.scarlettjoubert.noteswithcomments.databinding.NoteItemBinding
import com.scarlettjoubert.noteswithcomments.ui.notes.NoteViewHolder
import java.text.SimpleDateFormat
import java.util.*

class CommentsAdapter(
    private val onClick: (CommentsDto) -> Unit,
    private val deleteComment: (CommentsDto) -> Unit,
    private val context: Context,
): ListAdapter<CommentsDto, NoteViewHolder>(DiffUtilItemCallbackNotes()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context))
        return NoteViewHolder(binding)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = getItem(position)
        val longTime = item?.created!!
        val date = Date(longTime)
        val format = SimpleDateFormat(context.getString(R.string.date_format))

        with(holder.binding){
            textViewNoteTopic.isVisible = false
            imageViewCommentsCounter.isVisible = false
            textViewCommentsCounter.isVisible = false
            textViewNote.text = item.text
            textViewNoteCreated.text = format.format(date)
            cardViewItem.setOnClickListener{
              item?.let { onClick(item) }
            }
            imageViewDeleteNote.setOnClickListener {
                item?.let { deleteComment(item) }
            }
        }
    }
}

class DiffUtilItemCallbackNotes : DiffUtil.ItemCallback<CommentsDto>() {
    override fun areItemsTheSame(oldItem: CommentsDto, newItem: CommentsDto): Boolean =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: CommentsDto, newItem: CommentsDto): Boolean =
        oldItem == newItem
}