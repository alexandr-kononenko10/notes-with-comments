package com.scarlettjoubert.noteswithcomments.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.scarlettjoubert.noteswithcomments.*
import com.scarlettjoubert.noteswithcomments.data.dbnotes.Notes
import com.scarlettjoubert.noteswithcomments.databinding.FragmentNotesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NotesViewModel by viewModels()
    private lateinit var adapter: NotesAdapter
    private val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater)

        adapter = NotesAdapter { note ->
            onClick(note)
        }
        binding.notesRecyclerview.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.noteFlow.collect { notes ->
                adapter.submitList(notes)
            }
        }

        binding.buttonAddNote.setOnClickListener {
            lifecycleScope.launch { viewModel.test() }
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onClick(note: Notes) {
        bundle.putString(TOPIC, note.topic)
        bundle.putString(TEXT, note.text)
        bundle.putInt(ID, note.id!!)
        bundle.putLong(CREATED, note.created)
        findNavController().navigate(R.id.action_navigation_notes_to_editNoteFragment, bundle)

    }

}