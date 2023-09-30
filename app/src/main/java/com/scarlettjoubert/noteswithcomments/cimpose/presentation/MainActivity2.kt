package com.scarlettjoubert.noteswithcomments.cimpose.presentation

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.editnote.EditNoteScreen
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.home.HomeScreen
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.ui.theme.NotesWithCommentsTheme
import com.scarlettjoubert.noteswithcomments.domain.entity.Note
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NotesWithCommentsTheme {
                    HomeScreen()
            }
        }
    }
}
