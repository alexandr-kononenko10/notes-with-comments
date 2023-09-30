package com.scarlettjoubert.noteswithcomments.cimpose.presentation.notes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.ui.theme.Yellow
import com.scarlettjoubert.noteswithcomments.domain.entity.Note

@Composable
fun NoteItem(
    text:String,
    topic:String,
    created:String,
    onItemClick:() -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable( onClick = onItemClick),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Yellow
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Color.DarkGray
        )
    )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            //topic
            Text(
                text = topic,
                maxLines = 2,
                fontSize = 22.sp,
                color = Color.Black
            )
            Spacer(
                modifier = Modifier
                    .height(6.dp)
                    .padding(4.dp)
            )
            //text
            Text(
                text = text,
                maxLines = 7,
                fontSize = 16.sp,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                // created time
                Text(
                    text = created,
                    maxLines = 1,
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }
        }

    }
}
