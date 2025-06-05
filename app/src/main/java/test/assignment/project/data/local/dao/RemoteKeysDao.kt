package test.assignment.project.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import test.assignment.project.data.local.entities.RemoteKeys
import test.assignment.project.domain.model.SearchCategory

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKeys)

    @Query("SELECT * FROM remote_keys WHERE `query` = :query AND category = :category")
    suspend fun remoteKeyByQueryCategory(query: String?, category: SearchCategory?): RemoteKeys

    @Query("DELETE FROM remote_keys WHERE `query` = :query AND category = :category")
    suspend fun deleteByQueryCategory(query: String?, category: SearchCategory?)
}