package com.scarlettjoubert.noteswithcomments.domain.usecases

import com.scarlettjoubert.noteswithcomments.domain.entity.Topic
import com.scarlettjoubert.noteswithcomments.domain.repository.NotesRepositoryInterface
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetAllTopicsStateFlowUseCase @Inject constructor(
    private val repository: NotesRepositoryInterface,
) {
    operator fun invoke():StateFlow<List<Topic>> {
       return repository.getListOfTopics()
    }
}