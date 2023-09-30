package com.scarlettjoubert.noteswithcomments.ui.notes

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.scarlettjoubert.noteswithcomments.*
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.MainActivity2
import com.scarlettjoubert.noteswithcomments.data.dbnotes.NotesDto
import com.scarlettjoubert.noteswithcomments.databinding.FragmentNotesBinding
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
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

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_notes_menu, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                findNavController().navigate(R.id.action_navigation_notes_to_searchNoteFragment)
            }
        }
        return true
    }

    @ExperimentalMaterial3Api
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater)

        adapter = NotesAdapter (
            { note -> onClick(note) },
            { note -> delete(note) },
            viewModel.commentsRepositoryImpl,
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

        binding.buttonGoCompose.setOnClickListener{
            startActivity(
                Intent(requireContext(), MainActivity2::class.java)
            )
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onClick(note: NotesDto) {
        bundle.putString(TOPIC, note.topic)
        bundle.putString(TEXT, note.text)
        bundle.putInt(ID, note.id!!)
        bundle.putLong(CREATED, note.created)
        findNavController().navigate(R.id.action_navigation_notes_to_editNoteFragment, bundle)
    }

    private fun delete(note:NotesDto){
        viewModel.delete(note.id!!)
    }
}