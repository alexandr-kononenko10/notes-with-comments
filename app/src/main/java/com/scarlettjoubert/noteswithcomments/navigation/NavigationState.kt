package com.scarlettjoubert.noteswithcomments.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.scarlettjoubert.noteswithcomments.domain.entity.Comment
import com.scarlettjoubert.noteswithcomments.domain.entity.Note

class NavigationState(
    val navHostController: NavHostController
) {
    fun navigateTo(route: String) {
        navHostController.navigate(route) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToNotesFromTopic(topicName:String){
        navHostController.navigate(Screen.NotesFromTopicScreen.getRouteWithArgs(topicName))
    }

    fun navigateToEditNoteWithArgs(note: Note) {
        navHostController.navigate(Screen.EditNote.getRouteWithArgs(note))
    }
    fun navigateToCreateNote(){
        navHostController.navigate(Screen.EditNote.getRouteWithNoArgs())
    }


    fun navigateToCommentScreen(toNote:Int){
        navHostController.navigate(Screen.CommentScreen.getRouteWithArgs(toNote))
    }

    fun navigateToEditCommentScreen(comment: Comment){
        navHostController.navigate(Screen.EditCommentScreen.getRouteWithComment( comment))
    }
    fun navigateToCreateComment(noteId:Int){
        navHostController.navigate(Screen.EditCommentScreen.getRouteWithComment(noteId))
    }


}
@ExperimentalAnimationApi
@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberAnimatedNavController(),
): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}