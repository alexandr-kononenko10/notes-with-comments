package com.scarlettjoubert.noteswithcomments.ui.searchnote

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.scarlettjoubert.noteswithcomments.ID
import com.scarlettjoubert.noteswithcomments.R
import com.scarlettjoubert.noteswithcomments.TEXT
import com.scarlettjoubert.noteswithcomments.TOPIC
import com.scarlettjoubert.noteswithcomments.data.dbnotes.Notes
import com.scarlettjoubert.noteswithcomments.databinding.FragmentSearchNoteBinding
import com.scarlettjoubert.noteswithcomments.ui.notes.ItemDecorator
import com.scarlettjoubert.noteswithcomments.ui.notes.NotesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchNoteFragment : Fragment() {
    private var _binding: FragmentSearchNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NotesAdapter
    private lateinit var bundle: Bundle


    private val viewModel: SearchNoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchNoteBinding.inflate(inflater)
        bundle = Bundle()
        adapter = NotesAdapter(
            { note -> onClick(note) },
            { note -> delete(note) },
            viewModel.commentsRepository,
            requireContext()
        )
        binding.recycleViewSearch.adapter = adapter
        binding.recycleViewSearch.addItemDecoration(
            ItemDecorator(resources.getDimensionPixelSize(R.dimen.activity_horizontal_margin))
        )

        binding.editTextTextSearhline.addTextChangedListener((object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = binding.editTextTextSearhline.text.toString()
                if (query.isNotEmpty()) {
                    val result = viewModel.search(query)
                    viewLifecycleOwner.lifecycleScope.launch {
                        result.collect {
                            adapter.submitList(it)
                        }
                    }
                }else adapter.submitList(emptyList())
            }
        }
                ))

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
        findNavController().navigate(R.id.action_searchNoteFragment_to_editNoteFragment, bundle)
    }

    private fun delete(note: Notes) {
        viewModel.delete(note.id!!)
    }

}