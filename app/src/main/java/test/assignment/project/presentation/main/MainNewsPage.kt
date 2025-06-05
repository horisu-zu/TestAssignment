package test.assignment.project.presentation.main

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import test.assignment.project.presentation.common.NewsPage
import test.assignment.project.presentation.viewmodel.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNewsPage(
    modifier: Modifier = Modifier,
    newsViewModel: NewsViewModel = hiltViewModel(),
) {
    val newsItems = newsViewModel.searchNewsFlow.collectAsLazyPagingItems()

    var query by remember { mutableStateOf("") }
    LaunchedEffect(query) {
        snapshotFlow { query }
            .distinctUntilChanged()
            .collectLatest { newsViewModel.updateQuery(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    MainTextField(
                        query = query,
                        onQueryChange = { newValue -> query = newValue }
                    )
                }
            )
        }, modifier = modifier
    ) { innerPadding ->
        NewsPage(
            newsItems = newsItems,
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding(),
                start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                end = innerPadding.calculateEndPadding(LayoutDirection.Ltr))
        )
    }
}