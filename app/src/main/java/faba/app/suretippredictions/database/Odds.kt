package faba.app.suretippredictions.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import faba.app.suretippredictions.models.odds.Bookmaker
import faba.app.suretippredictions.models.odds.Fixture

@Entity(tableName = "odds_table")
data class Odds(
    @PrimaryKey val id: Int,
    val date: String,
    @TypeConverters(Converter::class) val fixture: Fixture?,
    @TypeConverters(Converter::class) val bookmakers: List<Bookmaker>
)