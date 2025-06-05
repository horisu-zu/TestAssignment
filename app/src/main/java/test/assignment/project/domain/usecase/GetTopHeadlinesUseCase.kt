package test.assignment.project.domain.usecase

import test.assignment.project.domain.model.NewsResponse
import test.assignment.project.data.remote.NewsApi
import javax.inject.Inject

class GetTopHeadlinesUseCase @Inject constructor(
    private val newsRepository: NewsApi
) {
    suspend fun execute(
        country: String? = null,
        category: String? = null,
        page: Int? = null,
        pageSize: Int? = null
    ): NewsResponse = newsRepository.getTopHeadlines(
        country = country,
        category = category,
        page = page,
        pageSize = pageSize
    )
}