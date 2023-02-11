package com.scarlettjoubert.noteswithcomments.ui.newnote

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.scarlettjoubert.noteswithcomments.ADD_TO_TOPIC
import com.scarlettjoubert.noteswithcomments.R
import com.scarlettjoubert.noteswithcomments.databinding.FragmentNewNoteBinding
import com.scarlettjoubert.noteswithcomments.databinding.FragmentNotesBinding
import com.scarlettjoubert.noteswithcomments.ui.notes.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewNoteFragment : Fragment() {
    private var _binding: FragmentNewNoteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewNoteViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewNoteBinding.inflate(inflater)
        val isFromTopic = arguments?.getString(ADD_TO_TOPIC)
        if (isFromTopic !== null){
            binding.editTextNewNotesTopic.text = isFromTopic!!.toEditable()
        }


        return binding.root
    }

    override fun onDestroy() {
        insert()
        super.onDestroy()
        _binding = null
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    private fun insert() {
        val text = binding.editTextNewNote.text.toString()
        val topic = binding.editTextNewNotesTopic.text.toString()
        if (text.isNotEmpty() || topic.isNotEmpty()) {
                viewModel.insert(topic, text)
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.saved),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                requireContext(),
                resources.getString(R.string.empty_entry),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}