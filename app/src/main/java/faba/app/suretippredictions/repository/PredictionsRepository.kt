package faba.app.suretippredictions.repository

import androidx.annotation.WorkerThread
import com.apollographql.apollo.coroutines.await
import faba.app.suretippredictions.GetFixturesQuery
import faba.app.suretippredictions.ListPredictionsQuery
import faba.app.suretippredictions.database.Odds
import faba.app.suretippredictions.database.Prediction
import faba.app.suretippredictions.database.PredictionUpdate
import faba.app.suretippredictions.database.PredictionsDao
import faba.app.suretippredictions.service.Apollo
import faba.app.suretippredictions.service.SafeGuardApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PredictionsRepository @Inject constructor(
    private val api: Apollo,
    private val predictionsDao: PredictionsDao
) : SafeGuardApiRequest(){

    suspend fun listPredictions(date: String, nextTokenPred: String, nextTokenOdd: String): ListPredictionsQuery.Data {
        return apiRequest {
            api.getApolloClient().query(ListPredictionsQuery(date, nextTokenPred, nextTokenOdd)).await()
        }
    }

    suspend fun getFixtures(date: String, nextToken: String): GetFixturesQuery.Data {
        return apiRequest {
            api.getApolloClient().query(GetFixturesQuery(date, nextToken)).await()
        }
    }


    fun roomPredictionsList(date: String) = predictionsDao.getAllPredictionsByDate(date)
    fun roomOddsList(date: String) = predictionsDao.getAllOddsByDate(date)

    fun roomPredictionsAndOddsList(date: String) = predictionsDao.getAllPredictionsDistinct(date)

    fun getPredictionsRowCount(date: String) = predictionsDao.getRowCountPredTableDistinct(date)
    fun getOddsRowCount(date: String) = predictionsDao.getRowCountOddsTableDistinct(date)


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertPrediction(prediction: Prediction) {
        predictionsDao.insertPrediction(prediction)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertOdds(odds: Odds) {
        predictionsDao.insertOdds(odds)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updatePrediction(predictionUpdate: PredictionUpdate) {
        predictionsDao.updatePrediction(predictionUpdate)
    }




}
