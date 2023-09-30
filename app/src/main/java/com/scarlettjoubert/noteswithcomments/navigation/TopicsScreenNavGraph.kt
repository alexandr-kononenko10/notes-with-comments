package com.scarlettjoubert.noteswithcomments.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.notesfromtopic.NotesFromTopicViewModel
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.topics.TopicsViewModel

fun NavGraphBuilder.topicsScreenNavGraph(
    topicsScreenContent: @Composable (TopicsViewModel) -> Unit,
    notesFromTopicContent:@Composable (NotesFromTopicViewModel) -> Unit
) {
    navigation(
        startDestination = Screen.Notes.route,
        route = Screen.AllAboutTopics.route
    ) {
        composable(
            route = Screen.Topics.route,
            arguments = emptyList(),
            content = {
                val viewModel: TopicsViewModel = hiltViewModel()
                topicsScreenContent(viewModel) }
        )

        composable(
            route = Screen.NotesFromTopicScreen.route,
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
            arguments = listOf(
                navArgument(
                    name = Screen.KEY_TOPIC
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                }
            ),

            content = {
                val viewModel: NotesFromTopicViewModel = hiltViewModel()
                notesFromTopicContent(viewModel) }
        )
    }
}