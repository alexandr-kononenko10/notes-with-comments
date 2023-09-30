package com.scarlettjoubert.noteswithcomments.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.comments.CommentScreenViewModel
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.editcomment.EditCommentViewModel
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.editnote.EditNoteViewModel
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.notes.NotesViewModel
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.notesfromtopic.NotesFromTopicViewModel
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.searchingscreen.SearchingScreenViewModel
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.topics.TopicsViewModel

@ExperimentalAnimationApi
@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    notesScreenContent: @Composable (NotesViewModel) -> Unit,
    topicsScreenContent: @Composable (TopicsViewModel) -> Unit,
    editNoteScreenContent: @Composable (EditNoteViewModel) -> Unit,
    notesFromTopicContent: @Composable (NotesFromTopicViewModel) -> Unit,
    commentScreenContent: @Composable (CommentScreenViewModel) -> Unit,
    editCommentScreenContent: @Composable (EditCommentViewModel) -> Unit,
    searchingScreenContent: @Composable (SearchingScreenViewModel) -> Unit,
) {
    AnimatedNavHost(
        navController = navHostController,
        startDestination = Screen.Home.route
    ) {
        notesNavGraph(
            notesScreenContent = notesScreenContent,
            editCommentScreenContent = editCommentScreenContent,
            editNoteScreenContent = editNoteScreenContent,
            commentScreenContent = commentScreenContent
        )
        topicsScreenNavGraph(
            topicsScreenContent = topicsScreenContent,
            notesFromTopicContent = notesFromTopicContent
        )
        composable(
            route = Screen.SearchingScreen.route,
            arguments = emptyList(),
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 })
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 })
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -1000 })
            },

            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 })

            },
            content = {
                val viewModel: SearchingScreenViewModel = hiltViewModel()
                searchingScreenContent(viewModel)
            }
        )


    }
}