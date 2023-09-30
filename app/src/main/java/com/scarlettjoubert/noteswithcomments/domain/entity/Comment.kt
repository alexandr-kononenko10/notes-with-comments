package com.scarlettjoubert.noteswithcomments.domain.entity


data class Comment(
    val id: Int,
    val toNote: Int,
    val text: String,
    val created: String,
    val createdLong:Long
):SavingItem
