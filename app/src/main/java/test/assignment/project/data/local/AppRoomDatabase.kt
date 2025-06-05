package test.assignment.project.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import test.assignment.project.data.local.converter.TypeConverter
import test.assignment.project.data.local.dao.NewsDao
import test.assignment.project.data.local.dao.RemoteKeysDao
import test.assignment.project.data.local.dao.SourceDao
import test.assignment.project.data.local.entities.NewsEntity
import test.assignment.project.data.local.entities.RemoteKeys
import test.assignment.project.data.local.entities.SourceEntity

@Database(
    entities = [NewsEntity::class, SourceEntity::class, RemoteKeys::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun sourceDao(): SourceDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}