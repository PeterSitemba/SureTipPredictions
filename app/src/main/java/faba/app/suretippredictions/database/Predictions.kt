package faba.app.suretippredictions.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import faba.app.suretippredictions.models.events.Events

@Entity(tableName = "match_events_table")
data class MatchEvents(@PrimaryKey val id: Int, @ColumnInfo(name = "match_id") @TypeConverters(Converter::class) val matchEvents : List<Events>)
