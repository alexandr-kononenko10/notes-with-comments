package com.scarlettjoubert.noteswithcomments.cimpose.presentation.searchingscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import com.scarlettjoubert.noteswithcomments.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarSearchingScreen(
    onBackPressed:() -> Unit,

){
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.title_edit_note),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(imageVector = Icons.Default.ArrowBack , contentDescription = null)
            }
        }
    )
}


//@Composable
//private fun SearchLine(){
//    Box(Modifier.fillMaxSize(){
//        SearchB
//
//    }
//}