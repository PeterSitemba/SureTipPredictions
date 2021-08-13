package faba.app.suretippredictions.repository

import androidx.annotation.WorkerThread
import faba.app.suretippredictions.database.MatchEvents
import faba.app.suretippredictions.database.PredictionsDao
import faba.app.suretippredictions.service.RetrofitService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PredictionsRepository @Inject constructor(
    private val retrofitService: RetrofitService,
    private val predictionsDao: PredictionsDao
) {

    val roomMatchEventsList: Flow<MatchEvents> = predictionsDao.getAllMatchEvents()


    suspend fun getAllMatchEvents() = retrofitService.getMatchEventsForDateRange()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(matchEvents: MatchEvents) {
        predictionsDao.insert(matchEvents)
    }

}