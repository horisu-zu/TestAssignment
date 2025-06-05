package test.assignment.project.domain.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val author: String? = null,
    val content: String?,
    val description: String?,
    val publishedAt: Instant,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String?
)