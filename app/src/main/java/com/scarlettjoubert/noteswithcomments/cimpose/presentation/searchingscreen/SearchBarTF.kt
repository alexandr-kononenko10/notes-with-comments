package com.scarlettjoubert.noteswithcomments.cimpose.presentation.searchingscreen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.scarlettjoubert.noteswithcomments.R
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.ui.theme.Yellow

@Composable
fun SearchBarTF(
    viewModel: SearchingScreenViewModel,
    modifier: Modifier
) {
    var query by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = query,
        onValueChange = {
            query = it
            viewModel.findNote(query)
        },
        modifier = modifier.padding(end = 8.dp),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = Yellow.copy(
                    alpha = ContentAlpha.medium
                )
            )
        },
        trailingIcon = {
            IconButton(onClick =  {
                query = ""
            }) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    tint = Yellow
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedPlaceholderColor = MaterialTheme.colorScheme.secondary.copy(alpha = ContentAlpha.medium),
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.secondary.copy(alpha = ContentAlpha.medium),
            cursorColor = Yellow,
            unfocusedBorderColor = Yellow.copy(alpha = ContentAlpha.medium),
            focusedTextColor = Yellow,
            unfocusedTextColor = Yellow.copy(alpha = ContentAlpha.medium),

        ),
        placeholder = {
            Text(text = stringResource(id = R.string.searchline_placeholder))
        },
        shape = RoundedCornerShape(8.dp)
    )
}