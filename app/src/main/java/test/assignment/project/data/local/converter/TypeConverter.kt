package test.assignment.project.data.local.converter

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import test.assignment.project.domain.model.SearchCategory

class TypeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Instant? {
        return value?.let { Instant.fromEpochSeconds(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Instant?): Long? {
        return date?.epochSeconds
    }

    @TypeConverter
    fun fromSearchCategory(category: SearchCategory?): String? {
        return category?.name
    }

    @TypeConverter
    fun toSearchCategory(categoryName: String?): SearchCategory? {
        return categoryName?.let { name ->
            SearchCategory.entries.find { it.name == name }
        }
    }
}