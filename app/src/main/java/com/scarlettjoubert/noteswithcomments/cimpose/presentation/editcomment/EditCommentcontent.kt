package com.scarlettjoubert.noteswithcomments.cimpose.presentation.editcomment

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
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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

@ExperimentalMaterial3Api
@Composable
fun EditCommentContent(
    viewModel: EditCommentViewModel,
    paddingValues: PaddingValues,
    state: EditCommentScreenState,
) {

    val initialText = when (state) {
        is EditCommentScreenState.Editing -> state.comment.text
        else -> ""
    }
    var textFieldValue by rememberSaveable { mutableStateOf(initialText) }


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
                //text field
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = textFieldValue,
                    onValueChange = {
                        textFieldValue = it
                        viewModel.saveComment(textFieldValue)
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.edit_text),
                            fontSize = 16.sp,
                            color = Color.DarkGray
                        )
                    },
                    colors =  OutlinedTextFieldDefaults.colors(
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