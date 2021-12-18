package faba.app.suretippredictions.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import faba.app.core.ApiException
import faba.app.core.NoInternetException
import faba.app.suretippredictions.models.odds.Bookmaker
import faba.app.suretippredictions.models.odds.Fixture
import faba.app.suretippredictions.models.odds.Odds
import faba.app.suretippredictions.models.predictions.*
import faba.app.suretippredictions.repository.PredictionsRepository
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class PredictionsViewModel @Inject constructor(private val repository: PredictionsRepository) :
    ViewModel() {

    private val gson = Gson()

    val predictionListResponse = MutableLiveData<MutableList<Prediction>>()
    val oddsListResponse = MutableLiveData<MutableList<Odds>>()

    var predCounter = 0
    var oddCounter = 0

    fun listPredictions(date: String) {
        val predictionList = mutableListOf<Prediction>()
        val oddsList = mutableListOf<Odds>()
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    var response = repository.listPredictions(date, "", "")

                    while (true) {

                        response.let { data ->
                            data.listPredictions()?.items()?.forEach {

                                val prediction = Prediction(
                                    it!!.id(),
                                    it.predictionDate(),
                                    gson.fromJson(it.status() as String?, Status::class.java),
                                    gson.fromJson(it.score() as String?, Score::class.java),
                                    gson.fromJson(
                                        it.predictions() as String?,
                                        Predictions::class.java
                                    ),
                                    gson.fromJson(it.league() as String?, League::class.java),
                                    it.homeId(),
                                    it.homeName(),
                                    it.homeLogo(),
                                    gson.fromJson(it.homeLastFive() as String?, Last5::class.java),
                                    it.homeForm(),
                                    gson.fromJson(
                                        it.homeFixtures() as String?,
                                        Fixtures::class.java
                                    ),
                                    gson.fromJson(
                                        it.homeGoalsForTotal() as String?,
                                        GoalsTotal::class.java
                                    ),
                                    gson.fromJson(
                                        it.homeGoalsForAverage() as String?,
                                        GoalsAverage::class.java
                                    ),
                                    gson.fromJson(
                                        it.homeGoalsAgainstTotal() as String?,
                                        GoalsTotal::class.java
                                    ),
                                    gson.fromJson(
                                        it.homeGoalsAgainstAverage() as String?,
                                        GoalsAverage::class.java
                                    ),
                                    gson.fromJson(it.homeBiggest() as String?, Biggest::class.java),
                                    gson.fromJson(
                                        it.homeCleanSheet() as String?,
                                        CleanSheet::class.java
                                    ),
                                    gson.fromJson(
                                        it.homeFailedToScore() as String?,
                                        FailedToScore::class.java
                                    ),
                                    gson.fromJson(it.homePenalty() as String?, Penalty::class.java),
                                    it.awayId(),
                                    it.awayName(),
                                    it.awayLogo(),
                                    gson.fromJson(it.awayLastFive() as String?, Last5::class.java),
                                    it.awayForm(),
                                    gson.fromJson(
                                        it.awayFixtures() as String?,
                                        Fixtures::class.java
                                    ),
                                    gson.fromJson(
                                        it.awayGoalsForTotal() as String?,
                                        GoalsTotal::class.java
                                    ),
                                    gson.fromJson(
                                        it.awayGoalsForAverage() as String?,
                                        GoalsAverage::class.java
                                    ),
                                    gson.fromJson(
                                        it.awayGoalsAgainstTotal() as String?,
                                        GoalsTotal::class.java
                                    ),
                                    gson.fromJson(
                                        it.awayGoalsAgainstAverage() as String?,
                                        GoalsAverage::class.java
                                    ),
                                    gson.fromJson(it.awayBiggest() as String?, Biggest::class.java),
                                    gson.fromJson(
                                        it.awayCleanSheet() as String?,
                                        CleanSheet::class.java
                                    ),
                                    gson.fromJson(
                                        it.awayFailedToScore() as String?,
                                        FailedToScore::class.java
                                    ),
                                    gson.fromJson(it.awayPenalty() as String?, Penalty::class.java),
                                    gson.fromJson(
                                        it.comparison() as String?,
                                        Comparison::class.java
                                    ),
                                    gson.fromJson(
                                        it.h2h() as String?,
                                        Array<FixturesH2H>::class.java
                                    )
                                        .toMutableList()

                                )

                                if(!predictionList.any{ prediction -> prediction.id == it.id()}){
                                    predictionList.add(prediction)
                                }
                                predCounter++

                            }


                            //add paging here as well

                            data.listOdds()?.items()?.forEach {
                                val odds = Odds(
                                    it.id(),
                                    it.oddDate(),
                                    gson.fromJson(it.fixture() as String?, Fixture::class.java),
                                    gson.fromJson(
                                        it.bookmaker() as String?,
                                        Array<Bookmaker>::class.java
                                    )
                                        .toList()
                                )

                                if(!oddsList.any{ odd -> odd.id == it.id()}){
                                    oddsList.add(odds)
                                }
                                oddCounter++
                            }

                        }

                        if (response.listPredictions()?.nextToken() == null && response.listOdds()
                                ?.nextToken() == null
                        ) break

                        if (response.listPredictions()?.nextToken() == null && response.listOdds()
                                ?.nextToken() != null
                        ) {
                            response = repository.listPredictions(
                                date,
                                "",
                                response.listOdds()?.nextToken()!!
                            )

                        } else if (response.listPredictions()
                                ?.nextToken() != null && response.listOdds()?.nextToken() == null
                        ) {
                            response = repository.listPredictions(
                                date,
                                response.listPredictions()?.nextToken()!!,
                                ""
                            )

                        } else {
                            response = repository.listPredictions(
                                date,
                                response.listPredictions()?.nextToken()!!,
                                response.listOdds()?.nextToken()!!
                            )

                        }


                    }


                }

                withContext(Dispatchers.Main) {

                    Log.e("Pred Counter ", predCounter.toString())
                    Log.e("Odds Counter ", oddCounter.toString())

                    oddsListResponse.value = oddsList
                    predictionListResponse.value = predictionList

                }

                /* withContext(Dispatchers.Main){

                 }*/


            } catch (e: ApiException) {
                //progressListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                //progressListener?.onFailure(e.message!!)
            }
        }
    }


}
