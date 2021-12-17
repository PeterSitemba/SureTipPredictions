package faba.app.suretippredictions.viewmodels

import androidx.lifecycle.*
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import faba.app.core.ApiException
import faba.app.core.NoInternetException
import faba.app.suretippredictions.models.predictions.*
import faba.app.suretippredictions.repository.PredictionsRepository
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class PredictionsViewModel @Inject constructor(private val repository: PredictionsRepository) :
    ViewModel() {

    private val gson = Gson()

    val predictionListResponse = MutableLiveData<MutableList<Prediction>>()

    fun listPredictions(date: String) {
        val predictionList = mutableListOf<Prediction>()
        viewModelScope.launch {
            try {
                val response = repository.listPredictions(date)
                response.let { data ->

                    data.listPredictions()?.items()?.forEach {

                        val prediction = Prediction(
                            it!!.id(),
                            it.date()!!,
                            gson.fromJson(it.status() as String?, Status::class.java),
                            gson.fromJson(it.score() as String?, Score::class.java),
                            gson.fromJson(it.predictions() as String?, Predictions::class.java),
                            gson.fromJson(it.league() as String?, League::class.java),
                            it.homeId(),
                            it.homeName(),
                            it.homeLogo(),
                            gson.fromJson(it.homeLastFive() as String?, Last5::class.java),
                            it.homeForm(),
                            gson.fromJson(it.homeFixtures() as String?, Fixtures::class.java),
                            gson.fromJson(it.homeGoalsForTotal() as String?, GoalsTotal::class.java),
                            gson.fromJson(it.homeGoalsForAverage() as String?, GoalsAverage::class.java),
                            gson.fromJson(it.homeGoalsAgainstTotal() as String?, GoalsTotal::class.java),
                            gson.fromJson(it.homeGoalsAgainstAverage() as String?, GoalsAverage::class.java),
                            gson.fromJson(it.homeBiggest() as String?, Biggest::class.java),
                            gson.fromJson(it.homeCleanSheet() as String?, CleanSheet::class.java),
                            gson.fromJson(it.homeFailedToScore() as String?, FailedToScore::class.java),
                            gson.fromJson(it.homePenalty() as String?, Penalty::class.java),
                            it.awayId(),
                            it.awayName(),
                            it.awayLogo(),
                            gson.fromJson(it.awayLastFive() as String?, Last5::class.java),
                            it.awayForm(),
                            gson.fromJson(it.awayFixtures() as String?, Fixtures::class.java),
                            gson.fromJson(it.awayGoalsForTotal() as String?, GoalsTotal::class.java),
                            gson.fromJson(it.awayGoalsForAverage() as String?, GoalsAverage::class.java),
                            gson.fromJson(it.awayGoalsAgainstTotal() as String?, GoalsTotal::class.java),
                            gson.fromJson(it.awayGoalsAgainstAverage() as String?, GoalsAverage::class.java),
                            gson.fromJson(it.awayBiggest() as String?, Biggest::class.java),
                            gson.fromJson(it.awayCleanSheet() as String?, CleanSheet::class.java),
                            gson.fromJson(it.awayFailedToScore() as String?, FailedToScore::class.java),
                            gson.fromJson(it.awayPenalty() as String?, Penalty::class.java),
                            gson.fromJson(it.comparison() as String?, Comparison::class.java),
                            gson.fromJson(it.h2h() as String?, Array<FixturesH2H>::class.java).toMutableList()

                        )
                        predictionList.add(prediction)
                    }

                    predictionListResponse.value = predictionList
                }

            } catch (e: ApiException) {
                //progressListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                //progressListener?.onFailure(e.message!!)
            }
        }
    }


}
