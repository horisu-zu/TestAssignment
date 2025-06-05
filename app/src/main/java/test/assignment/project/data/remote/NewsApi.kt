package test.assignment.project.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import test.assignment.project.domain.model.NewsResponse

interface NewsApi {

    @GET("everything")
    suspend fun getNews(
        @Query("sources") sources: String = "the-verge",
        @Query("q") query: String? = null,
        @Query("page") page: Int? = 1,
        @Query("pageSize") pageSize: Int? = 20,
        @Query("sortBy") sortBy: String? = "publishedAt",
        @Query("language") language: String? = "en"
    ): NewsResponse

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String? = "us",
        @Query("category") category: String? = null,
        @Query("page") page: Int? = 1,
        @Query("pageSize") pageSize: Int? = 20
    ): NewsResponse
}