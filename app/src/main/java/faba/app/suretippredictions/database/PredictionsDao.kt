package faba.app.suretippredictions.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/*
@Dao
interface PredictionsDao {

    @Query("SELECT * FROM match_events_table ORDER BY match_id ASC")
    fun getAllMatchEvents(): Flow<List<MatchEvents>>

    @Query("SELECT * FROM match_events_table WHERE match_date = :match_date ORDER BY match_id ASC")
    fun getAllMatchEventsByDate(match_date : String): Flow<List<MatchEvents>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(events: MatchEvents)

    @Query("DELETE FROM match_events_table")
    suspend fun deleteAll()

}*/
