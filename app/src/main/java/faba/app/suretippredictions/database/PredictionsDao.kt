package faba.app.suretippredictions.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface PredictionsDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPrediction(prediction: Iterable<Prediction>)

    @Transaction
    @Query("SELECT id,date, status, goals, predictions,homeName,homeLogo,awayName,awayLogo,odds,league FROM predictions_table WHERE date = :date")
    fun getAllPredictionsByDate(date: String): Flow<List<Prediction>>

    fun getAllPredictionsDistinct(date: String) = getAllPredictionsByDate(date).distinctUntilChanged()

    @Query("DELETE FROM predictions_table")
    suspend fun deleteAllPredictions()


    //pred count
    @Query("SELECT COUNT(id) FROM predictions_table WHERE date = :date")
    fun getRowCountPredTable(date: String): Flow<Int?>?

    //distinct
    fun getRowCountPredTableDistinct(date: String) = getRowCountPredTable(date)?.distinctUntilChanged()

    //update query
    @Update(entity = Prediction::class)
    suspend fun updatePrediction(predictionUpdate: Iterable<PredictionUpdate>)

    @Update(entity = Prediction::class)
    suspend fun updatePredictionOdds(predictionUpdateOdds: Iterable<PredictionUpdateOdds>)
}
