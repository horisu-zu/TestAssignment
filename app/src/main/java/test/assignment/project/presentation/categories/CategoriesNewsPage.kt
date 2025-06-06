package test.assignment.project.presentation.categories

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import test.assignment.project.domain.model.SearchCategory
import test.assignment.project.presentation.common.NewsPage
import test.assignment.project.presentation.viewmodel.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesNewsPage(
    modifier: Modifier = Modifier,
    newsViewModel: NewsViewModel = hiltViewModel()
) {
    val tabs = SearchCategory.entries.filter { it != SearchCategory.NONE }
        .map { it.displayValue.replaceFirstChar { it.uppercase() } }
    val pagerState = rememberPagerState { tabs.size }
    val coroutineScope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        CategoriesTabRow(
            tabs = tabs,
            selectedTab = pagerState.currentPage,
            onTabSelected = { newPage ->
                coroutineScope.launch {
                    pagerState.animateScrollToPage(
                        page = newPage,
                        animationSpec = tween(
                            durationMillis = 500,
                            easing = FastOutSlowInEasing
                        )
                    )
                }
            }
        )
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val category = SearchCategory.entries[page]
            val newsItems = newsViewModel.getCategoryNewsFlow(
                category = category
            ).collectAsLazyPagingItems()

            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = {
                    category.let {
                        coroutineScope.launch {
                            try {
                                isRefreshing = true
                                delay(300)
                                newsItems.refresh()
                            } finally {
                                isRefreshing = false
                            }
                        }
                    }
                }
            ) {
                NewsPage(newsItems = newsItems)
            }
        }
    }
}