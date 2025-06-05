package test.assignment.project.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import test.assignment.project.domain.model.Source

@Entity(tableName = "sources")
data class SourceEntity(
    val id: String?,
    @PrimaryKey val name: String
) {
    companion object {
        fun Source.toEntity(): SourceEntity {
            return SourceEntity(
                id = this.id,
                name = this.name
            )
        }
    }
}
