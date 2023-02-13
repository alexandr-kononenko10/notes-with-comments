package com.scarlettjoubert.noteswithcomments.ui.notesfromtopic

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.scarlettjoubert.noteswithcomments.*
import com.scarlettjoubert.noteswithcomments.data.dbnotes.Notes
import com.scarlettjoubert.noteswithcomments.databinding.FragmentNotesBinding
import com.scarlettjoubert.noteswithcomments.databinding.FragmentNotesFromTopicBinding
import com.scarlettjoubert.noteswithcomments.ui.notes.ItemDecorator
import com.scarlettjoubert.noteswithcomments.ui.notes.NotesAdapter
import com.scarlettjoubert.noteswithcomments.ui.notes.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFromTopicFragment : Fragment() {

    private var _binding: FragmentNotesFromTopicBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NotesFromTopicViewModel by viewModels()
    private lateinit var adapter: NotesAdapter
    private val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesFromTopicBinding.inflate(inflater)
        val topic = arguments?.getString(TOPIC)
        bundle.putString(ADD_TO_TOPIC, topic)

        adapter = NotesAdapter (
            { note -> onClick(note) },
            { note -> delete(note) },
            viewModel.commentsRepository,
            requireContext())
        binding.notesFromTopicRecyclerview.adapter = adapter
        binding.notesFromTopicRecyclerview.addItemDecoration(
            ItemDecorator(resources.getDimensionPixelSize(R.dimen.activity_horizontal_margin))
        )
        binding.buttonAddNoteAtTopic.setOnClickListener {
            findNavController().navigate(R.id.action_notesFromTopicFragment_to_newNoteFragment, bundle)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getNotes(topic!!).collect{
                adapter.submitList(it)
            }
        }




        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun onClick(note: Notes) {
        bundle.putString(TOPIC, note.topic)
        bundle.putString(TEXT, note.text)
        bundle.putInt(ID, note.id!!)
        bundle.putLong(com.scarlettjoubert.noteswithcomments.CREATED, note.created)
        findNavController().navigate(R.id.action_notesFromTopicFragment_to_editNoteFragment, bundle)
    }

    private fun delete(note: Notes){
        viewModel.delete(note.id!!)
    }
}