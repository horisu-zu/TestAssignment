package test.assignment.project.domain.usecase

import test.assignment.project.domain.model.NewsResponse
import test.assignment.project.data.remote.NewsApi
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsApi
) {
    suspend fun execute(
        query: String? = null,
        page: Int?,
        pageSize: Int?,
        sortBy: String? = "publishedAt",
        language: String? = "en"
    ): NewsResponse {
        val response = newsRepository.getNews(
            query = query,
            page = page,
            pageSize = pageSize,
            sortBy = sortBy,
            language = language
        )
        return response
    }
}