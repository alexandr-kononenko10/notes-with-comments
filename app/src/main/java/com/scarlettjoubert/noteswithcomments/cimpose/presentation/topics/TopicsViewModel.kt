package com.scarlettjoubert.noteswithcomments.cimpose.presentation.topics

import android.util.Log
import androidx.lifecycle.ViewModel
import com.scarlettjoubert.noteswithcomments.data.NotesRepository
import com.scarlettjoubert.noteswithcomments.domain.usecases.GetAllTopicsStateFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class TopicsViewModel @Inject constructor(
   getAllTopicsStateFlowUseCase: GetAllTopicsStateFlowUseCase
) : ViewModel() {

    val screenState = getAllTopicsStateFlowUseCase().map {
        TopicsScreenState.Topics(
            topics = it
        ) as TopicsScreenState
    }

}