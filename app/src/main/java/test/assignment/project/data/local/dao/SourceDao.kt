package test.assignment.project.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import test.assignment.project.data.local.entities.SourceEntity

@Dao
interface SourceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSources(news: List<SourceEntity>)

    @Delete
    suspend fun deleteSources(news: SourceEntity)

    @Query("DELETE FROM sources")
    suspend fun clearSources()

    @Query("SELECT * FROM sources")
    fun getSources(): PagingSource<Int, SourceEntity>
}