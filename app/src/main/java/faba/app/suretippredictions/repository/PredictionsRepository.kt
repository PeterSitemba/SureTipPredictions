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

    val roomMatchEventsList: Flow<List<MatchEvents>> = predictionsDao.getAllMatchEvents()

    //val roomMatchEventsListByDate: Flow<List<MatchEvents>> = predictionsDao.getAllMatchEventsByDate(setMatchDate())


    suspend fun getAllMatchEvents() = retrofitService.getMatchEventsForDateRange()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(matchEvents: MatchEvents) {
        predictionsDao.insert(matchEvents)
    }

    fun roomMatchEventsListByDate(match_date : String) : Flow<List<MatchEvents>>{
        return predictionsDao.getAllMatchEventsByDate(match_date)
    }

}