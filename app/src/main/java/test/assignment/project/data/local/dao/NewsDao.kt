package test.assignment.project.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import test.assignment.project.data.local.entities.NewsEntity
import test.assignment.project.domain.model.SearchCategory

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: List<NewsEntity>)

    @Query("DELETE FROM news WHERE category = :category")
    fun clearNewsByCategory(category: SearchCategory?)

    @Query("SELECT * FROM news WHERE category = :category ORDER BY publishedAt DESC")
    fun getNewsByCategory(category: SearchCategory): PagingSource<Int, NewsEntity>

    @Query("""
        SELECT * FROM news 
        WHERE category = 'NONE' 
        AND (:query = '' OR title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%')
        ORDER BY publishedAt DESC
    """)
    fun searchNews(query: String): PagingSource<Int, NewsEntity>
}