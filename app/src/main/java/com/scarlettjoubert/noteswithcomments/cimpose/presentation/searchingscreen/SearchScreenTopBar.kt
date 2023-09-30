package com.scarlettjoubert.noteswithcomments.cimpose.presentation.searchingscreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.ui.theme.Yellow

@Composable
fun SearchScreenTopBar(
    onBackPressed: () -> Unit,
    viewModel: SearchingScreenViewModel,
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(end = 8.dp)
    ) {
        IconButton(onClick = { onBackPressed() }) {
            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Yellow)
        }
        Spacer(modifier = Modifier.width(4.dp))

        SearchBarTF(
            viewModel = viewModel,
            modifier = Modifier.weight(1F)
        )
    }
}