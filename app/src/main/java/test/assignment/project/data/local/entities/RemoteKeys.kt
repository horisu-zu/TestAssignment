package test.assignment.project.data.local.entities

import androidx.room.Entity
import test.assignment.project.domain.model.SearchCategory

@Entity(tableName = "remote_keys", primaryKeys = ["query", "category"])
data class RemoteKeys(
    val query: String,
    val category: SearchCategory = SearchCategory.NONE,
    val nextKey: Int? = null
)
