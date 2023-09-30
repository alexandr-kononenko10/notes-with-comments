@file:OptIn(ExperimentalMaterial3Api::class)

package com.scarlettjoubert.noteswithcomments.cimpose.presentation.topics

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.scarlettjoubert.noteswithcomments.R
import com.scarlettjoubert.noteswithcomments.cimpose.presentation.home.TopBarHome

@ExperimentalMaterial3Api
@Composable
fun TopicsScreen(
    viewModel: TopicsViewModel,
    onSearchLineClick: () -> Unit,
    onTopicItemClick: (topic:String) -> Unit
) {

    val screenState = viewModel.screenState.collectAsState(initial = TopicsScreenState.Initial)

    Scaffold(
        topBar = {
            TopBarHome(
                onSearchLineClick = { onSearchLineClick() },
                title = R.string.title_topics
            )
        }
    ) { paddingValues ->
        when (val currentState = screenState.value) {
            is TopicsScreenState.Initial -> {}
            is TopicsScreenState.Topics -> {
                TopicColumn(
                    listOfTopics = currentState.topics,
                    paddingValues = paddingValues,
                    onTopicItemClick = { topicName ->
                        onTopicItemClick(topicName)
                    }
                )
            }

        }
    }
}
