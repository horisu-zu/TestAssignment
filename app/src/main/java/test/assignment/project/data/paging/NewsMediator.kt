package test.assignment.project.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import retrofit2.HttpException
import test.assignment.project.data.local.AppRoomDatabase
import test.assignment.project.data.local.entities.NewsEntity
import test.assignment.project.data.local.entities.NewsEntity.Companion.toEntity
import test.assignment.project.data.local.entities.RemoteKeys
import test.assignment.project.data.local.entities.SourceEntity.Companion.toEntity
import test.assignment.project.domain.model.SearchCategory
import test.assignment.project.domain.usecase.GetNewsUseCase
import test.assignment.project.domain.usecase.GetTopHeadlinesUseCase
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewsMediator @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val appRoomDatabase: AppRoomDatabase,
    private val query: String,
    private val category: SearchCategory
): RemoteMediator<Int, NewsEntity>() {
    val newsDao = appRoomDatabase.newsDao()
    val sourceDao = appRoomDatabase.sourceDao()
    val remoteKeysDao = appRoomDatabase.remoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsEntity>
    ): MediatorResult {
        return try {
            val page = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = appRoomDatabase.withTransaction {
                        remoteKeysDao.remoteKeyByQueryCategory(
                            query = query,
                            category = category
                        )
                    }

                    if (remoteKey.nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    remoteKey.nextKey
                }
            }

            val response = if(category != SearchCategory.NONE) {
                getTopHeadlinesUseCase.execute(
                    category = category.displayValue,
                    page = page,
                    pageSize = state.config.pageSize
                )
            } else {
                getNewsUseCase.execute(
                    query = query,
                    page = page,
                    pageSize = state.config.pageSize
                )
            }

            appRoomDatabase.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    newsDao.clearNewsByCategory(category)
                    sourceDao.clearSources()
                    remoteKeysDao.deleteByQueryCategory(query, category)
                }

                val nextKey = if (response.articles.isEmpty() || response.articles.size < state.config.pageSize) {
                    null
                } else { page + 1 }

                remoteKeysDao.insertOrReplace(
                    RemoteKeys(
                        query = query,
                        category = category,
                        nextKey = nextKey
                    )
                )

                newsDao.insertNews(response.articles.map { it.toEntity(category = category) })
                sourceDao.insertSources(response.articles.map { it.source.toEntity() })
            }

            MediatorResult.Success(
                endOfPaginationReached = response.articles.isEmpty() || response.articles.size < state.config.pageSize
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}