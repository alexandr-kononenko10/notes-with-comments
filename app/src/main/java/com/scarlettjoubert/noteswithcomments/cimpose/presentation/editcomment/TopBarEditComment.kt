@file:OptIn(ExperimentalMaterial3Api::class)

package com.scarlettjoubert.noteswithcomments.cimpose.presentation.editcomment

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
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

@Composable
fun TopBarEditComment(
    onBackPressed:()->Unit,
    onDeletePressed:()->Unit,
    onSharePressed:()->Unit

) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.title_edit_comment),
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
        },
        actions = {
            IconButton(onClick = { onDeletePressed() }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = Yellow
                )
            }

            IconButton(onClick = { onSharePressed() }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                    tint = Yellow
                )
            }
        }
    )
}
