@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.scarlettjoubert.noteswithcomments.cimpose.presentation.editnote

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.scarlettjoubert.noteswithcomments.R
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.ui.theme.Yellow
import com.scarlettjoubert.noteswithcomments.domain.entity.Comment

@Composable
fun EditNoteScreen(
    viewModel: EditNoteViewModel,
    onBackPressed: () -> Unit,
    onAddCommentClick: (Int) -> Unit,
    onShowCommentsClick: (Int) -> Unit,
    onLastCommentClick: (Comment) -> Unit,
) {

    val state = viewModel.state.collectAsState(EditNoteScreenState.Initial)
    val cotext = LocalContext.current

    var topicFieldValue by rememberSaveable { mutableStateOf("") }
    var textFieldValue by rememberSaveable { mutableStateOf("") }

    when (val currentState = state.value) {
        is EditNoteScreenState.Initial -> {}
        is EditNoteScreenState.Creating -> {}
        is EditNoteScreenState.Editing -> {
            topicFieldValue = currentState.note.topic
            textFieldValue = currentState.note.text
        }
    }



    BackHandler {
        viewModel.onBackPressed {
            onBackPressed()
        }
    }

    when (
        val currentState = state.value) {
        is EditNoteScreenState.Initial -> {}
        else -> {
            Scaffold(
                topBar = {
                    TopBarEditNote(
                        onBackPressed = {
                            viewModel.onBackPressed {
                                onBackPressed()
                            }
                        },
                        onDeletePressed = {
                            viewModel.deleteNote()
                            onBackPressed()
                        },
                        onSharePressed = {
                            viewModel.shareNote(cotext)
                        })
                }
            ) { paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .imePadding(),
                    contentPadding = PaddingValues(
                        start = 12.dp,
                        end = 12.dp,
                        top = 8.dp,
                        bottom = 12.dp
                    ),
                    reverseLayout = true,
                    verticalArrangement = Arrangement.Top,

                    ) {
                    if (currentState is EditNoteScreenState.Editing) {
                        //Последний комментанрий
                        item {
                            LastComment(
                                comment = currentState.lastComment,
                                toNote = currentState.note.id ?: throw IllegalArgumentException(),
                                onAddCommentClick = onAddCommentClick,
                                onShowCommentsClick = onShowCommentsClick,
                                onLastCommentClick = onLastCommentClick
                            )

                        }

                        item {
                            LastCommentHeader()
                        }
                    }
                    //редактирование заметки
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 200.dp)
                                .imePadding(),
                            colors = CardDefaults.cardColors(
                                containerColor = Yellow,
                                contentColor = Color.Black
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            //topic field
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = topicFieldValue,
                                onValueChange = {
                                    topicFieldValue = it
                                    viewModel.saveNote(
                                        text = textFieldValue,
                                        topic = topicFieldValue
                                    )
                                },
                                placeholder = {
                                    Text(
                                        text = stringResource(id = R.string.edit_topic),
                                        fontSize = 24.sp,
                                        color = Color.DarkGray

                                    )
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                    focusedContainerColor = Yellow,
                                    unfocusedContainerColor = Yellow,
                                    focusedBorderColor = Yellow,
                                    unfocusedBorderColor = Yellow,
                                ),
                                textStyle = TextStyle(
                                    fontSize = 24.sp
                                )
                            )

                            //text field
                            TextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = textFieldValue,
                                onValueChange = {
                                    textFieldValue = it
                                    viewModel.saveNote(
                                        text = textFieldValue,
                                        topic = topicFieldValue
                                    )
                                },
                                placeholder = {
                                    Text(
                                        text = stringResource(id = R.string.edit_text),
                                        fontSize = 16.sp,
                                        color = Color.DarkGray
                                    )
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                    focusedContainerColor = Yellow,
                                    unfocusedContainerColor = Yellow,
                                    focusedBorderColor = Yellow,
                                    unfocusedBorderColor = Yellow,
                                ),
                                textStyle = TextStyle(
                                    fontSize = 16.sp
                                )
                            )
                        }

                    }

                }
            }
        }
    }
}

