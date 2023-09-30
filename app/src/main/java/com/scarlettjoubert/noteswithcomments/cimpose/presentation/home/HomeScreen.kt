@file:OptIn(ExperimentalMaterial3Api::class)

package com.scarlettjoubert.noteswithcomments.cimpose.presentation.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.scarlettjoubert.noteswithcomments.R
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.comments.CommentScreen
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.editcomment.EditCommentScreen
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.editnote.EditNoteScreen
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.notes.NotesScreen
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.notesfromtopic.NotesFromTopic
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.searchingscreen.SearchingScreen
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.topics.TopicsScreen
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.ui.theme.Yellow
import com.scarlettjoubert.noteswithcomments.domain.entity.Comment
import com.scarlettjoubert.noteswithcomments.navigation.AppNavGraph
import com.scarlettjoubert.noteswithcomments.navigation.Screen
import com.scarlettjoubert.noteswithcomments.navigation.rememberNavigationState

@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
) {
    val navigationState = rememberNavigationState()
    val bottomBarState = remember { MutableTransitionState(true) }
    val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    when (navBackStackEntry?.destination?.route) {
        Screen.Notes.route -> bottomBarState.targetState = true
        Screen.Topics.route -> bottomBarState.targetState = true
        else ->bottomBarState.targetState = false
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(visibleState = bottomBarState) {
                BottomAppBar(
                ) {
                    val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
                    val items = listOf(
                        BottomNavigationItem.Notes,
                        BottomNavigationItem.Topics
                    )

                    items.forEach { item ->
                        val selected = navBackStackEntry?.destination?.hierarchy?.any {
                            it.route == item.screen.route
                        } ?: false
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                if (!selected) navigationState.navigateTo(item.screen.route)
                            },
                            label = {
                                Text(text = stringResource(id = item.titleResId), color = Yellow)
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = stringResource(id = item.titleResId),
                                    tint = Yellow
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.onSecondary
                            )
                        )
                    }

                }
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    Snackbar(
                        modifier = Modifier.padding(12.dp),
                        action = {
                            TextButton(onClick = { data.performAction() }) {
                                data.visuals.actionLabel?.let { Text(text = it) }
                            }
                        }
                    ) {
                        Text(text = data.visuals.message)
                    }
                }
            )
        }
    ) { paddingValues ->
        AppNavGraph(navHostController = navigationState.navHostController,
            notesScreenContent = {viewModel ->
                NotesScreen(
                    viewModel = viewModel,
                    paddingValues = paddingValues,
                    scope = scope,
                    snackbarHostState = snackbarHostState,
                    onSearchLineClick = { navigationState.navigateTo(Screen.SearchingScreen.route) },
                    onItemClick = { note ->
                        navigationState.navigateToEditNoteWithArgs(note)
                    },
                    onFABClick = { navigationState.navigateToCreateNote() })
            },
            topicsScreenContent = {viewModel ->
                TopicsScreen(
                    viewModel = viewModel,
                    onSearchLineClick = {
                        navigationState.navigateTo(Screen.SearchingScreen.route)
                    },
                    onTopicItemClick = { topicName ->
                        navigationState.navigateToNotesFromTopic(topicName)
                    }
                )
            }, editNoteScreenContent = { viewModel ->
                EditNoteScreen(
                    viewModel = viewModel,
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    },
                    onAddCommentClick = { noteId ->
                        navigationState.navigateToCreateComment(noteId)
                    },
                    onShowCommentsClick = { toNote ->
                        navigationState.navigateToCommentScreen(toNote)
                    },
                    onLastCommentClick = { commentItem ->
                        navigationState.navigateToEditCommentScreen(commentItem)
                    })
            },
            notesFromTopicContent = { viewModel ->
                NotesFromTopic(
                    viewModel = viewModel,
                    scope = scope,
                    snackbarHostState = snackbarHostState,
                    onSearchLineClick = {
                        navigationState.navigateTo(Screen.SearchingScreen.route)
                    },
                    onBackPressed = {
                        navigationState.navHostController.popBackStack()
                    },
                    onItemClick = { note ->
                        navigationState.navigateToEditNoteWithArgs(note)
                    })
            },
            commentScreenContent = { viewModel ->
                CommentScreen(
                    viewModel = viewModel,
                    scope = scope,
                    snackbarHostState = snackbarHostState,
                    onBackPressed = { navigationState.navHostController.popBackStack() },
                    onFABClick = { noteId ->
                        navigationState.navigateToCreateComment(noteId)
                    },
                    onItemClick = { commentItem ->
                        navigationState.navigateToEditCommentScreen(commentItem)
                    }
                )
            },
            editCommentScreenContent = { viewModel ->
                EditCommentScreen(
                    viewModel = viewModel,
                    onBackPressed = { navigationState.navHostController.popBackStack() },
                )
            },
            searchingScreenContent = { viewModel ->
                SearchingScreen(viewModel = viewModel,
                    scope = scope,
                    snackbarHostState = snackbarHostState,
                    onItemClick = { note -> navigationState.navigateToEditNoteWithArgs(note) },
                    onBackPressed = { navigationState.navHostController.popBackStack() })
            }
        )

    }
}
