package faba.app.suretippredictions.repository

import androidx.annotation.WorkerThread
import com.apollographql.apollo.coroutines.await
import faba.app.suretippredictions.GetFixturesQuery
import faba.app.suretippredictions.ListOddsQuery
import faba.app.suretippredictions.ListPredictionsQuery
import faba.app.suretippredictions.database.*
import faba.app.suretippredictions.service.Apollo
import faba.app.suretippredictions.service.SafeGuardApiRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PredictionsRepository @Inject constructor(
    private val api: Apollo,
    private val predictionsDao: PredictionsDao
) : SafeGuardApiRequest(){

    suspend fun listPredictions(date: String, nextToken: String): ListPredictionsQuery.Data {
        return apiRequest {
            api.getApolloClient().query(ListPredictionsQuery(date, nextToken)).await()
        }
    }

    suspend fun listOdds(date: String, nextToken: String): ListOddsQuery.Data {
        return apiRequest {
            api.getApolloClient().query(ListOddsQuery(date, nextToken)).await()
        }
    }

    suspend fun getFixtures(date: String, nextToken: String): GetFixturesQuery.Data {
        return apiRequest {
            api.getApolloClient().query(GetFixturesQuery(date, nextToken)).await()
        }
    }


    fun roomPredictionsList(date: String) = predictionsDao.getAllPredictionsDistinct(date)
    fun getPredictionsRowCount(date: String) = predictionsDao.getRowCountPredTableDistinct(date)


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertPrediction(prediction: Iterable<Prediction>) {
        predictionsDao.insertPrediction(prediction)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updatePrediction(predictionUpdate: Iterable<PredictionUpdate>) {
        predictionsDao.updatePrediction(predictionUpdate)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updatePredictionOdds(predictionUpdateOdds: Iterable<PredictionUpdateOdds>) {
        predictionsDao.updatePredictionOdds(predictionUpdateOdds)
    }



}
