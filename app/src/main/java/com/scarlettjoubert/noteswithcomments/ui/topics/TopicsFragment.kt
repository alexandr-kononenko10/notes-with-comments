package com.scarlettjoubert.noteswithcomments.ui.topics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.scarlettjoubert.noteswithcomments.R
import com.scarlettjoubert.noteswithcomments.TOPIC
import com.scarlettjoubert.noteswithcomments.databinding.FragmentTopicsBinding
import com.scarlettjoubert.noteswithcomments.ui.notes.ItemDecorator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopicsFragment : Fragment() {

    private var _binding: FragmentTopicsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TopicsViewModel by viewModels()
    private lateinit var adapter: TopicsAdapter
    private val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopicsBinding.inflate(inflater)
        adapter = TopicsAdapter(
            { item -> onClick(item) },
            viewModel.notesRepository
        )
        binding.topicsRecycleView.adapter = adapter
        binding.topicsRecycleView.addItemDecoration(
            ItemDecorator(resources.getDimensionPixelSize(R.dimen.custom_vertical_margin))
        )

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.topicsFlow.collect{topics ->
                val list = topics.toSet().toList()
                adapter.submitList(list)
            }
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onClick(item: String) {
        bundle.putString(TOPIC, item)
      findNavController().navigate(R.id.action_navigation_topics_to_notesFromTopicFragment, bundle)
    }
}