package test.assignment.project.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import test.assignment.project.data.local.entities.NewsEntity
import test.assignment.project.utils.WebIntent

@Composable
fun NewsPage(
    newsItems: LazyPagingItems<NewsEntity>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    if(newsItems.loadState.refresh is LoadState.Loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if(newsItems.loadState.refresh is LoadState.Error) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Error: ${(newsItems.loadState.refresh as LoadState.Error).error.message}")
        }
    } else {
        LazyColumn(
            modifier = modifier
        ) {
            when (val refreshState = newsItems.loadState.refresh) {
                is LoadState.Error -> {
                    item {
                        Text("Error: ${refreshState.error}")
                    }
                }
                else -> {
                    items(
                        count = newsItems.itemCount,
                        key = newsItems.itemKey { it.title },
                    ) { index ->
                        val newsItem = newsItems[index] ?: return@items

                        NewsItem(
                            news = newsItem,
                            onClick = { url -> WebIntent.openUrlCustomTab(context, url) },
                            showDivider = index != newsItems.itemCount - 1
                        )
                    }
                    newsItems.apply {
                        if(loadState.append is LoadState.Loading) {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) { CircularProgressIndicator() }
                            }
                        }
                    }
                }
            }
        }
    }
}