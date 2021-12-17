package faba.app.suretippredictions.repository

import com.apollographql.apollo.coroutines.await
import faba.app.suretippredictions.ListPredictionsQuery
import faba.app.suretippredictions.service.Apollo
import faba.app.suretippredictions.service.SafeGuardApiRequest
import javax.inject.Inject

class PredictionsRepository @Inject constructor(
    private val api: Apollo
) : SafeGuardApiRequest(){
    suspend fun listPredictions(date: String): ListPredictionsQuery.Data {
        return apiRequest {
            api.getApolloClient().query(ListPredictionsQuery(date)).await()
        }
    }
}
