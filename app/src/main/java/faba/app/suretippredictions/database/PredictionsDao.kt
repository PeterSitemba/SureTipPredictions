package faba.app.suretippredictions.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface PredictionsDao {

    /* @Query("SELECT * FROM match_events_table ORDER BY match_id ASC")
     fun getAllMatchEvents(): Flow<List<MatchEvents>>*/

    //predictions
    @Query("SELECT * FROM predictions_table WHERE date = :date")
    fun getAllPredictionsByDate(date: String): Flow<List<Prediction>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPrediction(prediction: Iterable<Prediction>)

    @Query("DELETE FROM predictions_table")
    suspend fun deleteAllPredictions()


    //odds
    @Query("SELECT * FROM odds_table WHERE date = :date")
    fun getAllOddsByDate(date: String): Flow<List<Odds>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOdds(odds: Iterable<Odds>)

    @Query("DELETE FROM odds_table")
    suspend fun deleteAllPOdds()

    //both
    @Transaction
    @Query("SELECT id,date, status, goals, predictions,homeName,homeLogo,awayName,awayLogo FROM predictions_table WHERE date = :date")
    fun getAllPredictionsAndOddsByDate(date: String): Flow<List<PredictionAndOdds>>

    fun getAllPredictionsDistinct(date: String) = getAllPredictionsAndOddsByDate(date).distinctUntilChanged()



    //pred count
    @Query("SELECT COUNT(id) FROM predictions_table WHERE date = :date")
    fun getRowCountPredTable(date: String): Flow<Int?>?


    //odd count
    @Query("SELECT COUNT(id) FROM odds_table WHERE date = :date")
    fun getRowCountOddsTable(date: String): Flow<Int?>?

    //distinct
    fun getRowCountPredTableDistinct(date: String) = getRowCountPredTable(date)?.distinctUntilChanged()
    fun getRowCountOddsTableDistinct(date: String) = getRowCountOddsTable(date)?.distinctUntilChanged()


    //update query
    @Update(entity = Prediction::class)
    suspend fun updatePrediction(predictionUpdate: Iterable<PredictionUpdate>)
}
