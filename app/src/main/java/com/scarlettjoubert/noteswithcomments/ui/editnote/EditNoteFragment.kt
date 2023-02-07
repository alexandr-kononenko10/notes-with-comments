package com.scarlettjoubert.noteswithcomments.ui.editnote


import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.scarlettjoubert.noteswithcomments.CREATED
import com.scarlettjoubert.noteswithcomments.ID
import com.scarlettjoubert.noteswithcomments.TEXT
import com.scarlettjoubert.noteswithcomments.TOPIC
import com.scarlettjoubert.noteswithcomments.databinding.FragmentEditNoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditNoteFragment : Fragment() {
    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditNoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditNoteBinding.inflate(inflater)
        val id = arguments?.getInt(ID)
        val topic = arguments?.getString(TOPIC)
        val text = arguments?.getString(TEXT)
        val created = arguments?.getLong(CREATED)

        binding.editTextNote.text = text!!.toEditable()
        binding.editTextNotesTopic.text= topic!!.toEditable()


        return binding.root
    }

    override fun onDestroy() {
        update()
        super.onDestroy()
        _binding = null
    }

    private fun update(){
        Log.i("update", "updated")
        val id = arguments?.getInt(ID)
        val topic = binding.editTextNotesTopic.text
        val text = binding.editTextNote.text
        val created = arguments?.getLong(CREATED)
        viewModel.update(id!!, topic.toString(), text.toString(), created!! )
    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

}