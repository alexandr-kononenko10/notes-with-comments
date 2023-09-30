package com.scarlettjoubert.noteswithcomments.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.animation.navigation
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.comments.CommentScreenViewModel
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.editcomment.EditCommentViewModel
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.editnote.EditNoteViewModel
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.notes.NotesViewModel

@ExperimentalAnimationApi
fun NavGraphBuilder.notesNavGraph(
    notesScreenContent: @Composable (NotesViewModel) -> Unit,
    commentScreenContent: @Composable (CommentScreenViewModel) -> Unit,
    editCommentScreenContent: @Composable (EditCommentViewModel) -> Unit,
    editNoteScreenContent: @Composable (EditNoteViewModel) -> Unit,
) {
    navigation(
        startDestination = Screen.Notes.route,
        route = Screen.Home.route
    ) {
        composable(
            route = Screen.Notes.route,
            arguments = emptyList(),
            content = {
                val viewModel: NotesViewModel = hiltViewModel()
                notesScreenContent(viewModel)
            }
        )
        editNoteNavGraph(
            editNoteScreenContent = editNoteScreenContent,
            commentScreenContent = commentScreenContent,
            editCommentScreenContent = editCommentScreenContent
        )
    }
}