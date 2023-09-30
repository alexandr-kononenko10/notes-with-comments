package com.scarlettjoubert.noteswithcomments.domain.usecases

import com.scarlettjoubert.noteswithcomments.domain.entity.Note
import com.scarlettjoubert.noteswithcomments.domain.entity.SavingStatus
import com.scarlettjoubert.noteswithcomments.domain.repository.NotesRepositoryInterface
import javax.inject.Inject

class SaveNoteUseCase @Inject constructor(
    private val repository: NotesRepositoryInterface
) {
    operator fun invoke(newNote: Note): SavingStatus {
      return try {
          repository.saveNte(newNote)
          SavingStatus.Success
      }
       catch (e:Exception){
           SavingStatus.UnknownError
       }
    }
}