package test.assignment.project.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant
import test.assignment.project.domain.model.Article
import test.assignment.project.domain.model.SearchCategory

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey val title: String,
    val author: String?,
    val content: String?,
    val category: SearchCategory?,
    val description: String?,
    val publishedAt: Instant,
    val sourceId: String?,
    val url: String,
    val urlToImage: String?
) {
    companion object {
        fun Article.toEntity(category: SearchCategory?): NewsEntity {
            return NewsEntity(
                title = this.title,
                author = this.author,
                content = this.content,
                category = category,
                description = this.description,
                publishedAt = this.publishedAt,
                sourceId = this.source.id,
                url = this.url,
                urlToImage = this.urlToImage
            )
        }
    }
}
