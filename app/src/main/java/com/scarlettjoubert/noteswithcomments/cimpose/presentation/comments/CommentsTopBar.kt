package com.scarlettjoubert.noteswithcomments.cimpose.presentation.comments

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.scarlettjoubert.noteswithcomments.R
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.ui.theme.Yellow

@ExperimentalMaterial3Api
@Composable
fun CommentsTopBar(
    onBackPressed:()-> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.comments),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Yellow
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Yellow
                )
            }
        }
    )
}

