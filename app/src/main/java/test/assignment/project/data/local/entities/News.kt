package test.assignment.project.data.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class News(
    @Embedded val news: NewsEntity,
    @Relation(
        parentColumn = "sourceId",
        entityColumn = "id"
    )
    val source: SourceEntity? = null
)
