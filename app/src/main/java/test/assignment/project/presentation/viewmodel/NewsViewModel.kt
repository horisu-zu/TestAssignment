package test.assignment.project.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import test.assignment.project.data.local.AppRoomDatabase
import test.assignment.project.data.local.entities.NewsEntity
import test.assignment.project.data.paging.NewsMediator
import test.assignment.project.domain.model.SearchCategory
import test.assignment.project.domain.usecase.GetNewsUseCase
import test.assignment.project.domain.usecase.GetTopHeadlinesUseCase
import test.assignment.project.presentation.common.NewsSearchType
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val appRoomDatabase: AppRoomDatabase,
    private val getNewsUseCase: GetNewsUseCase,
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase
): ViewModel() {

    private val queryFlow = MutableStateFlow<String>("")

    val searchNewsFlow: Flow<PagingData<NewsEntity>> = queryFlow
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            getNewsFlow(query = query)
        }.cachedIn(viewModelScope)

    fun getNewsFlow(
        query: String = "",
        category: SearchCategory = SearchCategory.NONE,
        searchType: NewsSearchType = NewsSearchType.QUERY
    ): Flow<PagingData<NewsEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 5,
                initialLoadSize = 20
            ),
            remoteMediator = NewsMediator(
                appRoomDatabase = appRoomDatabase,
                getNewsUseCase = getNewsUseCase,
                getTopHeadlinesUseCase = getTopHeadlinesUseCase,
                query = query,
                category = category
            ),
            pagingSourceFactory = {
                when(searchType) {
                    NewsSearchType.QUERY -> appRoomDatabase.newsDao().searchNews(query)
                    NewsSearchType.CATEGORIES -> appRoomDatabase.newsDao().getNewsByCategory(category)
                }
            }
        ).flow.cachedIn(viewModelScope)
    }

    fun updateQuery(query: String) {
        queryFlow.value = query
    }
}