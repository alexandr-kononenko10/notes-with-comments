package com.scarlettjoubert.noteswithcomments.ui.topics

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.scarlettjoubert.noteswithcomments.data.NotesRepository
import com.scarlettjoubert.noteswithcomments.databinding.TopicItemBinding

class TopicsAdapter(
    private val onClick: (String) -> Unit,
    val notesRepository: NotesRepository
) : ListAdapter<String, TopicsViewHolder>(DiffUtilItemCallbackTopics()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicsViewHolder {
        val binding = TopicItemBinding.inflate(LayoutInflater.from(parent.context))
        return TopicsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopicsViewHolder, position: Int) {
        val item = getItem(position)
        val counter = notesRepository.getNotesFromTopic(item)
Log.i("counter", counter.toString())
        with(holder.binding) {
            textViewTopicItem.text = item
            textViewTopicsCounter.text = counter.size.toString()
            cardViewItemTopic.setOnClickListener {
                item?.let { onClick(item) }
            }
        }

    }
}
class DiffUtilItemCallbackTopics: DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
        oldItem == newItem


    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
        oldItem == newItem
}