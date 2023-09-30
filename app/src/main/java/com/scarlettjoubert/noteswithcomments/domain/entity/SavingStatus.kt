package com.scarlettjoubert.noteswithcomments.domain.entity

sealed class SavingStatus{

    object Success:SavingStatus()

    object ErrorEmptyElement: SavingStatus()

    class DeletedItem(val deletedItem:SavingItem): SavingStatus()

    object UnknownError: SavingStatus()

}