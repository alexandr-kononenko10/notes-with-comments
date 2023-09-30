package com.scarlettjoubert.noteswithcomments.cimpose.presentation.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.scarlettjoubert.noteswithcomments.R
import com.scarlettjoubert.noteswithcomments.navigation.Screen

sealed class BottomNavigationItem(
    val screen: Screen,
    val titleResId: Int,
    val icon: Int
) {
    object Notes :
        BottomNavigationItem(
            screen = Screen.Home,
            titleResId = R.string.title_notes,
            icon = R.drawable.ic_baseline_notes_24
        )

    object Topics : BottomNavigationItem(
        screen = Screen.Topics,
        titleResId = R.string.title_topics,
        icon = R.drawable.ic_baseline_topic_24
    )
}
