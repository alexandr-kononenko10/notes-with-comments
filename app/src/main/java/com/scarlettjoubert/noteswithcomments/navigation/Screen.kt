package com.scarlettjoubert.noteswithcomments.navigation

import android.net.Uri
import com.google.gson.Gson
import com.scarlettjoubert.noteswithcomments.domain.entity.Comment
import com.scarlettjoubert.noteswithcomments.domain.entity.Note

sealed class Screen(
    val route: String,
) {

    object Home : Screen(ROUTE_HOME)

    object Notes : Screen(route = NOTES_ROUTE)
    object Topics : Screen(route = TOPICS_ROUTE)

    object AllAboutTopics : Screen(ALL_ABOUT_TOPICS_ROUTE)
    object EditingScreen : Screen(ROUTE_EDITING_SCREEN)

    object EditNote : Screen(ROUTE_EDIT_NOTE) {
        private const val ROUTE_EDIT_NOTE_FOR_ARG = "edit_note_route"

        fun getRouteWithArgs(note: Note): String {
            val noteGson = Gson().toJson(note)
            return "$ROUTE_EDIT_NOTE_FOR_ARG?$KEY_NOTE=${noteGson.encode()}" //Uri.encode нужен для экранирования спецсиволов в строке
        }

        fun getRouteWithNoArgs(): String {
            return ROUTE_EDIT_NOTE_FOR_ARG
        }
    }

    object NotesFromTopicScreen : Screen(ROUTE_NOTES_FROM_TOPIC) {
        private const val ROUTE_NOTES_FROM_TOPIC_FOR_ARG = "notes_from_topic_route"

        fun getRouteWithArgs(topicName: String): String {
            return "$ROUTE_NOTES_FROM_TOPIC_FOR_ARG?$KEY_TOPIC=${topicName.encode()}"
        }
    }

    object CommentScreen : Screen(COMMENTS_SCREEN_ROUTE) {
        private const val COMMENTS_SCREEN_ROUTE_ARG = "COMMENTS_SCREEN_ROUTE"

        fun getRouteWithArgs(toNoteId: Int): String {
            return "$COMMENTS_SCREEN_ROUTE_ARG/${toNoteId}"
        }
    }

    object EditCommentScreen : Screen(EDIT_COMMENT_SCREEN_ROUTE) {
        private const val EDIT_COMMENT_SCREEN_ROUTE_ARG = "edit_comment_route"

        fun getRouteWithComment(comment: Comment): String {
            val commentJson = Gson().toJson(comment)
            return "$EDIT_COMMENT_SCREEN_ROUTE_ARG?$KEY_EDIT_COMMENT=${commentJson.encode()}"
        }

        fun getRouteWithComment(id: Int): String {
            return "$EDIT_COMMENT_SCREEN_ROUTE_ARG?$KEY_NOTE_ID=${id}"
        }
    }

    object SearchingScreen:Screen(ROUTE_SEARCHING_SCREEN)

    companion object {
        private const val NOTES_ROUTE = "notes_route"
        private const val TOPICS_ROUTE = "topics_route"
        private const val ROUTE_HOME = "home_route"
        private const val ALL_ABOUT_NOTES_ROUTE = "all_about_notes_route"
        private const val ALL_ABOUT_TOPICS_ROUTE = "all_about_topics_route"
        private const val ROUTE_EDITING_SCREEN = "route_editing_screen"
        const val KEY_NOTE = "key_note"
        const val KEY_TOPIC = "key_topic"

        private const val ROUTE_EDIT_NOTE = "edit_note_route?$KEY_NOTE={$KEY_NOTE}"

        private const val ROUTE_NOTES_FROM_TOPIC = "notes_from_topic_route?$KEY_TOPIC={$KEY_TOPIC}"

        const val KEY_COMMENT = "key_comment"
        const val KEY_NOTE_ID = "key_note_id"
        private const val COMMENTS_SCREEN_ROUTE = "COMMENTS_SCREEN_ROUTE/{$KEY_COMMENT}"

        const val KEY_EDIT_COMMENT = "key_edit_comment"
        private const val EDIT_COMMENT_SCREEN_ROUTE = "edit_comment_route?$KEY_EDIT_COMMENT={$KEY_EDIT_COMMENT}&$KEY_NOTE_ID={$KEY_NOTE_ID}"

        private const val ROUTE_SEARCHING_SCREEN = "route_searching_screen"
    }
}

fun String.encode(): String {
    return Uri.encode(this)
}