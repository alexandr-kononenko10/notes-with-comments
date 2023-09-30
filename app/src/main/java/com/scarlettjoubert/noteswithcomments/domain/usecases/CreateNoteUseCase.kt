package com.scarlettjoubert.noteswithcomments.domain.usecases

import com.scarlettjoubert.noteswithcomments.domain.entity.SavingStatus
import com.scarlettjoubert.noteswithcomments.domain.repository.NotesRepositoryInterface
import javax.inject.Inject

class CreateNoteUseCase @Inject constructor(
    private val repository: NotesRepositoryInterface
) {
    operator fun invoke(): SavingStatus {
       return try {
            repository.createNote(
                topic = "",
                text = ""
            )
            SavingStatus.Success
        }catch (e:Exception){
           SavingStatus.UnknownError
        }
    }
}