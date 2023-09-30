package com.scarlettjoubert.noteswithcomments.cimpose.presentation.topics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.scarlettjoubert.noteswithcomments.domain.entity.Topic

@Composable
fun TopicColumn(
    listOfTopics: List<Topic>,
    paddingValues: PaddingValues,
    onTopicItemClick:(topic:String)->Unit
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 12.dp,
            end = 12.dp,
            bottom = 72.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = listOfTopics,
            key = { it.id }
        ) {
            TopicItem(
                topic = it.name,
                count = it.count,
                onTopicItemClick = {onTopicItemClick(it.name)}
            )
        }
    }
}