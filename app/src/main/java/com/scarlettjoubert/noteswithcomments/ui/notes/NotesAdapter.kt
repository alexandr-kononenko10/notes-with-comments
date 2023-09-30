package com.scarlettjoubert.noteswithcomments.ui.notes

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.scarlettjoubert.noteswithcomments.R
import com.scarlettjoubert.noteswithcomments.data.CommentsRepositoryImpl
import com.scarlettjoubert.noteswithcomments.data.dbnotes.NotesDto
import com.scarlettjoubert.noteswithcomments.databinding.NoteItemBinding
import java.text.SimpleDateFormat
import java.util.*

class NotesAdapter (
    private val onClick: (NotesDto) -> Unit,
    private val delete: (NotesDto) -> Unit,
    private val commentsRepositoryImpl: CommentsRepositoryImpl,
    private val context: Context,
): ListAdapter<NotesDto, NoteViewHolder>(DiffUtilItemCallbackNotes()) {

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
            textViewNoteTopic.text = item.topic
            textViewNote.text = item.text
            textViewNoteCreated.text = format.format(date)
            textViewCommentsCounter.text = commentsRepositoryImpl.getCommentsList(item.id!!).size.toString()
            cardViewItem.setOnClickListener{
              item?.let { onClick(item) }
            }
            imageViewDeleteNote.setOnClickListener {
                item?.let { delete(item) }
            }
        }
    }
}

class DiffUtilItemCallbackNotes : DiffUtil.ItemCallback<NotesDto>() {
    override fun areItemsTheSame(oldItem: NotesDto, newItem: NotesDto): Boolean =
        oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: NotesDto, newItem: NotesDto): Boolean =
        oldItem == newItem
}