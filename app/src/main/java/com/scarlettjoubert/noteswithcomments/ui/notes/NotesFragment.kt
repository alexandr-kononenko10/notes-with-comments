package com.scarlettjoubert.noteswithcomments.ui.notes

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_notes_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                findNavController().navigate(R.id.action_navigation_notes_to_searchNoteFragment)
            }
        }
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater)

        adapter = NotesAdapter (
            { note -> onClick(note) },
            { note -> delete(note) },
            viewModel.commentsRepository,
            requireContext())
        binding.notesRecyclerview.adapter = adapter
        binding.notesRecyclerview.addItemDecoration(
            ItemDecorator(resources.getDimensionPixelSize(R.dimen.activity_horizontal_margin))
        )

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.noteFlow.collect { notes ->
                adapter.submitList(notes)
            }
        }

        binding.buttonAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_notes_to_newNoteFragment)
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

    private fun delete(note:Notes){
        viewModel.delete(note.id!!)
    }
}