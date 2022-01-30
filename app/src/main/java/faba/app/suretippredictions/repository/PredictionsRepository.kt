package faba.app.suretippredictions.repository

import androidx.annotation.WorkerThread
import com.apollographql.apollo3.api.ApolloResponse
import faba.app.suretippredictions.GetFixturesQuery
import faba.app.suretippredictions.ListOddsQuery
import faba.app.suretippredictions.ListPredictionsQuery
import faba.app.suretippredictions.database.Prediction
import faba.app.suretippredictions.database.PredictionUpdate
import faba.app.suretippredictions.database.PredictionUpdateOdds
import faba.app.suretippredictions.database.PredictionsDao
import faba.app.suretippredictions.service.Apollo
import faba.app.suretippredictions.service.SafeGuardApiRequest
import javax.inject.Inject

class PredictionsRepository @Inject constructor(
    private val api: Apollo,
    private val predictionsDao: PredictionsDao
) : SafeGuardApiRequest() {

    //region API requests
    suspend fun listPredictions(
        date: String,
        nextToken: String
    ): ApolloResponse<ListPredictionsQuery.Data> {

        return api.getApolloClient().query(ListPredictionsQuery(date, nextToken)).execute()

    }

    suspend fun listOdds(date: String, nextToken: String): ApolloResponse<ListOddsQuery.Data> {
        return api.getApolloClient().query(ListOddsQuery(date, nextToken)).execute()
    }

    suspend fun getFixtures(
        date: String,
        nextToken: String
    ): ApolloResponse<GetFixturesQuery.Data> {
        return api.getApolloClient().query(GetFixturesQuery(date, nextToken)).execute()
    }
    //End region API requests


    //region Room DB functions
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
    // end region Room DB functions


}
