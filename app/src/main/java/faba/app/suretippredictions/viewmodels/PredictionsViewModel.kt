package faba.app.suretippredictions.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import faba.app.core.ApiException
import faba.app.core.NoInternetException
import faba.app.suretippredictions.database.Prediction
import faba.app.suretippredictions.models.odds.Bookmaker
import faba.app.suretippredictions.models.odds.Fixture
import faba.app.suretippredictions.database.Odds
import faba.app.suretippredictions.database.PredictionAndOdds
import faba.app.suretippredictions.database.PredictionUpdate
import faba.app.suretippredictions.models.fixtures.Goals
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

    val predCounterResponse = MutableLiveData<Int>()
    val oddCounterResponse = MutableLiveData<Int>()


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
                                    null,
                                    null,
                                    null,
                                    gson.fromJson(
                                        it.predictions() as String?,
                                        Predictions::class.java
                                    ),
                                    null,
                                    null,
                                    it.homeName(),
                                    it.homeLogo(),
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    it.awayName(),
                                    it.awayLogo(),
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null,
                                    null

                                )

                                if (!predictionList.any { prediction -> prediction.id == it.id() }) {
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

                                if (!oddsList.any { odd -> odd.id == it.id() }) {
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


                oddsList.let {
                    repository.insertOdds(it)
                }

                predictionList.let {
                    repository.insertPrediction(it)
                }




                withContext(Dispatchers.Main) {
                    predCounterResponse.value = predCounter
                    oddCounterResponse.value = oddCounter
                    //Log.e("Pred Counter ", predCounter.toString())
                    //Log.e("Odds Counter ", oddCounter.toString())
                }

            } catch (e: ApiException) {
                //progressListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                //progressListener?.onFailure(e.message!!)
            }
        }
    }

    fun updatePrediction(date: String) {
        val predictionUpdateList = mutableListOf<PredictionUpdate>()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response = repository.getFixtures(date, "")

                while (true) {

                    response.let { data ->
                        data.listFixtures()?.items()?.forEach {

                            val predictionUpdate = PredictionUpdate(
                                it.id(),
                                gson.fromJson(
                                    it.status() as String?,
                                    Status::class.java
                                ),
                                gson.fromJson(
                                    it.score() as String?,
                                    Score::class.java
                                ),
                                gson.fromJson(
                                    it.goals() as String?,
                                    Goals::class.java
                                )
                            )

                            if (!predictionUpdateList.any { predictionUpdateItem -> predictionUpdateItem.id == it.id() }) {
                                predictionUpdateList.add(predictionUpdate)
                            }


                        }

                    }

                    if (response.listFixtures()?.nextToken() == null) {
                        break
                    } else {
                        response = repository.getFixtures(
                            date,
                            response.listFixtures()?.nextToken()!!
                        )
                    }

                }


                predictionUpdateList.let {
                    repository.updatePrediction(it)
                }

            } catch (e: ApiException) {
                //progressListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                //progressListener?.onFailure(e.message!!)
            }
        }
    }


    fun roomPredictionsAndOddsList(date: String): LiveData<List<PredictionAndOdds>> {
        return repository.roomPredictionsAndOddsList(date).asLiveData()
    }

    fun roomPredictionsList(date: String): LiveData<List<Prediction>> {
        return repository.roomPredictionsList(date).asLiveData()
    }

    fun roomOddsList(date: String): LiveData<List<Odds>> {
        return repository.roomOddsList(date).asLiveData()
    }

    fun getPredictionsRowCount(date: String) = repository.getPredictionsRowCount(date)?.asLiveData()
    fun getOddsRowCount(date: String) = repository.getOddsRowCount(date)?.asLiveData()


}
