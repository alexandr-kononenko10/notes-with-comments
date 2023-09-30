package com.scarlettjoubert.noteswithcomments.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.gson.Gson
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.comments.CommentScreenViewModel
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.editcomment.EditCommentViewModel
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.editnote.EditNoteViewModel
import com.scarlettjoubert.noteswithcomments.domain.entity.Note

fun NavGraphBuilder.editNoteNavGraph(
    editNoteScreenContent: @Composable (EditNoteViewModel) -> Unit,
    commentScreenContent: @Composable (CommentScreenViewModel) -> Unit,
    editCommentScreenContent: @Composable (EditCommentViewModel) -> Unit,
) {
    navigation(
        startDestination = Screen.EditNote.route,
        route = Screen.EditingScreen.route
    ) {
        composable(
            route = Screen.EditNote.route,
            arguments = listOf(
                navArgument(
                    name = Screen.KEY_NOTE
                ) {
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                }
            ),
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { 1000 },
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideInVertically(
                    initialOffsetY = { 1000 },
                    animationSpec = tween(500)
                )
            },

            popExitTransition = {
                slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(500)
                )

            },
            content = {
                val viewModel = hiltViewModel<EditNoteViewModel>()
                editNoteScreenContent(viewModel)
            }
        )

        composable(
            route = Screen.CommentScreen.route,
            arguments = listOf(
                navArgument(
                    name = Screen.KEY_COMMENT
                ) {
                    type = NavType.IntType
                }
            ),
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { 1000 },
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideInVertically(
                    initialOffsetY = { 1000 },
                    animationSpec = tween(500)
                )
            },

            popExitTransition = {
                slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(500)
                )

            },
            content = {
                val viewModel = hiltViewModel<CommentScreenViewModel>()
                commentScreenContent(viewModel)
            }
        )

        composable(
            route = Screen.EditCommentScreen.route,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { 1000 },
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideInVertically(
                    initialOffsetY = { 1000 },
                    animationSpec = tween(500)
                )
            },

            popExitTransition = {
                slideOutVertically(
                    targetOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(500)
                )

            },
            arguments = listOf(
                navArgument(
                    name = Screen.KEY_EDIT_COMMENT
                ) {
                    nullable = true
                    defaultValue = null
                    type = NavType.StringType
                },
                navArgument(
                    name = Screen.KEY_NOTE_ID
                ) {
                    defaultValue = -1
                    type = NavType.IntType
                }
            ),
            content = {
                val viewModel = hiltViewModel<EditCommentViewModel>()
                editCommentScreenContent(viewModel)
            }
        )
    }
}