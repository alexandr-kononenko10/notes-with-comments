package com.scarlettjoubert.noteswithcomments.ui.editnote


import android.opengl.Visibility
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.scarlettjoubert.noteswithcomments.*
import com.scarlettjoubert.noteswithcomments.data.dbcomments.Comments
import com.scarlettjoubert.noteswithcomments.databinding.FragmentEditNoteBinding
import com.scarlettjoubert.noteswithcomments.ui.comments.CommentsAdapter
import com.scarlettjoubert.noteswithcomments.ui.notes.ItemDecorator

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditNoteFragment : Fragment() {
    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditNoteViewModel by viewModels()
    private lateinit var adapter: CommentsAdapter
    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_note_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share_button_toolbar -> {
                viewModel.shareNote(
                    id!!,
                    requireContext(),
                    binding.editTextNotesTopic.text.toString(),
                    binding.editTextNote.text.toString()
                )
            }
        }
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditNoteBinding.inflate(inflater)
        id = arguments?.getInt(ID)!!
        Log.i("id", id.toString())
        val topic = arguments?.getString(TOPIC)
        val text = arguments?.getString(TEXT)
        val created = arguments?.getLong(CREATED)

        binding.editTextNote.text = text!!.toEditable()
        binding.editTextNotesTopic.text = topic!!.toEditable()


        binding.commeentsRecycleView.addItemDecoration(
            ItemDecorator(resources.getDimensionPixelSize(R.dimen.custom_vertical_margin))
        )

        adapter = CommentsAdapter({ item -> onClick(item) },
            { item -> deleteComment(item) })
        binding.commeentsRecycleView.adapter = adapter

        if (id !== null) {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                val commentsFlow = viewModel.getComments(id!!)
                commentsFlow.collect { comments ->
                    adapter.submitList(comments)
                }
            }
        }

        binding.imageButtonAddComment.setOnClickListener {
            if (id !== null && binding.editTextComment.text.toString().isNotEmpty()) {
                lifecycleScope.launch {
                    viewModel.insertComment(
                        id!!,
                        binding.editTextComment.text.toString()
                    )
                }
                binding.editTextComment.text = null
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        update()
        super.onDestroy()
        _binding = null
    }

    private fun update() {
        Log.i("update", "updated")
        val id = arguments?.getInt(ID)
        val topic = binding.editTextNotesTopic.text
        val text = binding.editTextNote.text
        val created = arguments?.getLong(CREATED)
        viewModel.update(id!!, topic.toString(), text.toString(), created!!)
    }

    private fun deleteComment(item: Comments) {
        viewModel.deleteComment(item.id!!)
    }

    private fun onClick(item: Comments) {

    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

}